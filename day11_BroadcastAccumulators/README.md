# Day 11 — Broadcast and Accumulators

## Objective

The objective of Day 11 is to understand and practice:

- Broadcasting small reference data to Spark executors.
- Using an accumulator to count bad/invalid records.
- Understanding why normal driver variables should not be used for distributed updates.
- Combining broadcast data with RDD processing.
- Validating transactions against a small master/reference table.

## Topics Covered

1. Broadcast Variables
2. Accumulators
3. Distributed RDD Processing
4. Transaction Validation
5. Small Reference/Master Data
6. Driver vs Executor Processing
7. Aggregating Valid Transactions

## Project Structure

```text
day11_BroadcastAccumulators/
│
├── build.sbt
├── .gitignore
├── README.md
├── Output
│
├── data/
│   └── transactions.txt
│
└── src/
    ├── main/
    │   ├── resources/
    │   │   └── log4j2.properties
    │   └── scala/
    │       └── Day11BroadcastAccumulators.scala
```

## Input Data

File: `data/transactions.txt`

```text
T001 P001 5000
T002 P002 1500
T003 P003 2500
T004 P001 3000
T005 P999 1200
T006 P002 2000
T007 P004 4500
T008 P999 800
T009 P003 3500
T010 P004 5000
```

Format:

```text
Transaction_ID Product_ID Amount
```

## Running the Project

```bash
cd ~/scala-spark-30-days-practice/day11_BroadcastAccumulators
sbt run
```

## Actual Successful Output

The following output was produced during the successful Day 11 run:

```text
==============================================

# DAY 11 - BROADCAST AND ACCUMULATORS

Product Master:
P001 -> Laptop
P002 -> Mouse
P003 -> Keyboard
P004 -> Monitor

Broadcast variable created successfully.

Total Records : 10
Bad Records   : 2

## Validation Results:

T001  P001  Laptop      5000.00  VALID
T002  P002  Mouse       1500.00  VALID
T003  P003  Keyboard    2500.00  VALID
T004  P001  Laptop      3000.00  VALID
T005  P999  UNKNOWN     1200.00  INVALID
T006  P002  Mouse       2000.00  VALID
T007  P004  Monitor     4500.00  VALID
T008  P999  UNKNOWN      800.00  INVALID
T009  P003  Keyboard    3500.00  VALID
T010  P004  Monitor     5000.00  VALID

## Valid Transactions:

T001  P001  Laptop      5000.00
T002  P002  Mouse       1500.00
T003  P003  Keyboard    2500.00
T004  P001  Laptop      3000.00
T006  P002  Mouse       2000.00
T007  P004  Monitor     4500.00
T009  P003  Keyboard    3500.00
T010  P004  Monitor     5000.00

## Valid Transaction Total by Product:

P001  Laptop        8000.00
P002  Mouse         3500.00
P003  Keyboard      6000.00
P004  Monitor       9500.00

## Overall Statistics:

Total Transactions : 10
Valid Transactions : 8
Invalid Transactions : 2
Total Valid Amount : 27000.00
Bad Record Accumulator : 2

## Why Use an Accumulator?

Normal driver variables should not be used for distributed updates because executors run tasks independently.
Accumulator variables are designed for safely collecting distributed counters back to the driver.

## Why Use Broadcast?

Broadcast sends the small product reference data efficiently to executors instead of repeatedly sending it with task data.

==============================================
Day 11 execution completed successfully.
==============================================

[success] elapsed time: 4 s
```
