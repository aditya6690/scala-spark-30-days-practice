# Day 7 — Immutability, Lineage and Fault Tolerance

## Objective

- Create a multi-step RDD transformation chain
- Understand RDD immutability
- Understand RDD lineage
- Understand Spark fault tolerance
- Understand how Spark recomputes lost partitions

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- Java 17.0.20
- SBT
- Ubuntu / WSL2

## Project Structure

```text
day7_ImmutabilityLineage/
├── build.sbt
├── .gitignore
├── README.md
├── Output
├── data/
│   └── input.txt
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day7ImmutabilityLineage.scala
```

## RDD Transformation Chain

```text
numbersRDD
    |
    v
filter(_ % 2 == 0)
    |
    v
map(x => x * x)
    |
    v
filter(_ > 20)
    |
    v
resultRDD
```

## Immutability

The original RDD remains unchanged. Each transformation creates a new RDD.

```text
Original RDD:
List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

Derived RDD:
List(36, 64, 100)
```

## Lineage

The program uses `resultRDD.toDebugString` to display lineage.

Actual lineage observed:

```text
(12) MapPartitionsRDD[3] at filter at Day7ImmutabilityLineage.scala:45 []
|   MapPartitionsRDD[2] at map at Day7ImmutabilityLineage.scala:43 []
|   MapPartitionsRDD[1] at filter at Day7ImmutabilityLineage.scala:41 []
|   ParallelCollectionRDD[0] at parallelize at Day7ImmutabilityLineage.scala:30 []
```

Transformation flow:

```text
numbersRDD
    |
    v
filter(_ % 2 == 0)
    |
    v
map(x => x * x)
    |
    v
filter(_ > 20)
    |
    v
resultRDD
```

## Fault Tolerance

If a partition is lost, Spark can recompute that partition using the RDD lineage.

Conceptual recovery:

```text
Lost resultRDD partition
        <- filter(_ > 20)
        <- map(x => x * x)
        <- filter(_ % 2 == 0)
        <- numbersRDD
```

Spark can recompute the lost partition from its lineage rather than recomputing unrelated partitions.

## How to Run

```bash
cd ~/scala-spark-30-days-practice/day7_ImmutabilityLineage
sbt compile
sbt run
```

## Complete Actual Output

```text
========================================
DAY 7 - IMMUTABILITY, LINEAGE & FAULT TOLERANCE
===============================================

Spark Version : 3.5.3
Application   : Day7ImmutabilityLineage
Master        : local[*]

========== 1. BASE RDD ==========
Original RDD:
List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

========== 2. TRANSFORMATION CHAIN ==========
Even Numbers:
List(2, 4, 6, 8, 10)
Squared Numbers:
List(4, 16, 36, 64, 100)
Final Result:
List(36, 64, 100)

========== 3. IMMUTABILITY ==========
Original RDD remains unchanged:
List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
Derived RDD:
List(36, 64, 100)
RDDs are immutable: transformations create new RDDs.

========== 4. RDD LINEAGE ==========
Lineage of resultRDD:
(12) MapPartitionsRDD[3] at filter at Day7ImmutabilityLineage.scala:45 []
|   MapPartitionsRDD[2] at map at Day7ImmutabilityLineage.scala:43 []
|   MapPartitionsRDD[1] at filter at Day7ImmutabilityLineage.scala:41 []
|   ParallelCollectionRDD[0] at parallelize at Day7ImmutabilityLineage.scala:30 []

Transformation flow:
numbersRDD
|
v
filter(_ % 2 == 0)
|
v
map(x => x * x)
|
v
filter(_ > 20)
|
v
resultRDD

========== 5. FAULT TOLERANCE ==========
If a partition is lost, Spark can recompute
that partition using the RDD lineage.

Conceptual recovery:
Lost resultRDD partition
<- filter(_ > 20)
<- map(x => x * x)
<- filter(_ % 2 == 0)
<- numbersRDD

Spark does not need to recompute unrelated partitions.
It recomputes the lost partition from its lineage.

========== 6. PARTITIONS ==========
Original RDD Partitions : 12
Result RDD Partitions   : 12
Default Parallelism     : 12

========================================
Day 7 completed successfully!
Aditya completed Immutability, Lineage and Fault Tolerance.
========================================

[success] elapsed time: 5 s
```