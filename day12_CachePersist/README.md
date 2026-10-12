# Day 12 — Cache and Persist

## Objective

Day 12 covers:

- Cache an RDD reused by multiple actions.
- Compare `cache()` and `persist()`.
- Experiment with different storage levels.
- Explain when caching hurts performance.
- Reuse a cleaned transaction dataset in three reports.

## Technologies

- Scala 2.12.18
- Apache Spark 3.5.3
- Spark Core
- Spark SQL dependency
- SBT
- Java 17
- Linux / WSL2

## Project Structure

```text
day12_CachePersist/
├── build.sbt
├── .gitignore
├── Output
├── README.md
├── data/
│   └── transactions.txt
└── src/main/
    ├── resources/
    │   └── log4j2.properties
    └── scala/
        └── Day12CachePersist.scala
```

## Input Data

`data/transactions.txt`

```text
T001 C001 P001 5000
T002 C002 P002 1500
T003 C003 P003 2500
T004 C001 P001 3000
T005 C004 P004 4500
T006 C002 P002 2000
T007 C005 P005 1200
T008 C003 P003 3500
T009 C001 P001 4000
T010 C006 P004 5000
T011 C004 P004 2500
T012 C005 P005 1800
T013 C002 P002 2200
T014 C003 P003 1500
T015 C006 P004 3500
```

Format:

```text
Transaction_ID Customer_ID Product_ID Amount
```

## Running the Project

```bash
cd ~/scala-spark-30-days-practice/day12_CachePersist
sbt run
```

## Actual Successful Output

```text
==============================================
       DAY 12 - CACHE AND PERSIST
==============================================

Raw Transaction Count : 15

Cleaned Transaction Dataset Created.

Cache Status:
cleanedTransactions.cache() applied.
Default cache storage level: MEMORY_ONLY
Cleaned Transaction Count : 15

==============================================
REPORT 1 - CUSTOMER-WISE SALES
==============================================
C001  -> 12000.00
C002  -> 5700.00
C003  -> 7500.00
C004  -> 7000.00
C005  -> 3000.00
C006  -> 8500.00

==============================================
REPORT 2 - PRODUCT-WISE SALES
==============================================
P001  -> 12000.00
P002  -> 5700.00
P003  -> 7500.00
P004  -> 15500.00
P005  -> 3000.00

==============================================
REPORT 3 - OVERALL SALES
==============================================
Total Transactions : 15
Total Sales       : 43700.00
Average Sale      : 2913.33
Maximum Sale      : 5000.00

==============================================
CACHE VS PERSIST
==============================================
cache()  -> Equivalent to persist(StorageLevel.MEMORY_ONLY)
persist() -> Allows selecting a storage level.

Example storage levels:
MEMORY_ONLY
MEMORY_ONLY_SER
MEMORY_AND_DISK
DISK_ONLY

Persist Example:
Storage Level : MEMORY_AND_DISK
Persisted Transaction Count : 15

==============================================
WHEN CAN CACHING HURT PERFORMANCE?
==============================================
1. When the RDD is used only once.
2. When the dataset is too large for available memory.
3. When caching causes memory pressure and eviction.
4. When recomputation is cheaper than storing the data.
5. When unnecessary cached datasets consume executor memory.

==============================================
Day 12 execution completed successfully.
==============================================
[success] elapsed time: 4 s
```

