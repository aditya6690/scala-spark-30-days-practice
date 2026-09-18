# Day 10 — Partitioning

## Objective

Day 10 focuses on understanding Spark partitioning:

- Creating and inspecting partitions
- Viewing data distribution across partitions
- Using `repartition`
- Using `coalesce`
- Comparing `repartition` and `coalesce`
- Understanding partition counts and parallelism

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- Java 17.0.20
- SBT
- Ubuntu / WSL2

## Project Structure

```text
day10_Partitioning/
├── build.sbt
├── .gitignore
├── Output
├── README.md
├── data/
│   └── numbers.txt
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day10Partitioning.scala
```

## Input Data

Numbers from 1 to 20:

```text
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
```

## How to Run

```bash
cd ~/scala-spark-30-days-practice/day10_Partitioning
```

Compile:

```bash
sbt compile
```

Run:

```bash
sbt run
```

## Complete Actual Output

```text
========================================
DAY 10 - PARTITIONING
=====================

Spark Version : 3.5.3
Application   : Day10Partitioning
Master        : local[*]

========== 1. READ INPUT DATA ==========
Input Data:
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
Initial Partitions : 2

========== 2. DATA BY PARTITION ==========
Partition 0 -> 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
Partition 1 -> 13, 14, 15, 16, 17, 18, 19, 20

========== 3. REPARTITION ==========
Partitions after repartition : 4
Partition 0 -> 4, 8, 12, 16, 20
Partition 1 -> 1, 5, 9, 13, 17
Partition 2 -> 2, 6, 10, 14, 18
Partition 3 -> 3, 7, 11, 15, 19

========== 4. COALESCE ==========
Partitions after coalesce : 2
Partition 0 -> 4, 8, 12, 16, 20, 2, 6, 10, 14, 18
Partition 1 -> 1, 5, 9, 13, 17, 3, 7, 11, 15, 19

========== 5. REPARTITION VS COALESCE ==========
repartition(4) -> increases partitions to 4
coalesce(2)    -> reduces partitions to 2
repartition can cause a shuffle.
coalesce is mainly used to reduce partitions.

========== 6. PARTITION SUMMARY ==========
Initial RDD Partitions       : 2
Repartitioned RDD Partitions : 4
Coalesced RDD Partitions     : 2
Default Parallelism          : 12

========== 7. ACTION ==========
Total Records: 20

========================================
Day 10 completed successfully!
Aditya completed Partitioning practice.
=======================================

[success] elapsed time: 12 s
```


