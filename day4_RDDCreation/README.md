# Day 4 – RDD Creation and Basic Transformations

## Objective

The objective of Day 4 is to understand Apache Spark RDDs (Resilient Distributed Datasets), create RDDs from Scala collections and text files, and perform basic transformations and actions using Scala.

This project demonstrates how Spark processes distributed data using immutable RDDs.

## Topics Covered

- RDD from Scala collection using `parallelize()`
- RDD from text file using `textFile()`
- `map` transformation
- `filter` transformation
- `flatMap` transformation
- `reduce` action
- `reduceByKey` transformation
- Partitions and default parallelism
- Spark logging configuration

## Scenario

Build a small sales analytics application using Spark RDDs.

The application processes student marks and sales records using RDD transformations and actions.

## Project Structure

```text
day4_RDDCreation/
├── build.sbt
├── .gitignore
├── README.md
├── Output
├── data/
│   └── sales.txt
├── src/
│   └── main/
│       ├── resources/
│       │   └── log4j2.properties
│       └── scala/
│           └── Day4RDDCreation.scala
```

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- Spark Core
- Spark SQL
- SBT
- Java 17
- WSL2 / Ubuntu

## Input File

`data/sales.txt`

```text
Laptop 50000
Mouse 800
Keyboard 1500
Monitor 12000
Laptop 55000
Mouse 1000
Keyboard 1800
Monitor 15000
```

## Main RDD Operations

| Operation | Purpose |
|---|---|
| `parallelize()` | Creates an RDD from a Scala collection |
| `textFile()` | Creates an RDD from a text file |
| `map()` | Transforms every element |
| `filter()` | Selects elements based on a condition |
| `flatMap()` | Transforms and flattens elements |
| `reduce()` | Combines values into one result |
| `reduceByKey()` | Aggregates values having the same key |
| `getNumPartitions` | Returns the number of partitions |
| `defaultParallelism` | Shows Spark's default parallelism |

## Transformations and Actions

### Transformations

Used in this project:

- `map`
- `filter`
- `flatMap`
- `reduceByKey`

Transformations are lazy. Spark builds the execution plan but does not immediately execute the computation.

### Actions

Used in this project:

- `collect`
- `count`
- `reduce`

Actions trigger Spark job execution.

## How to Run

Navigate to the project:

```bash
cd ~/scala-spark-practice/day4_RDDCreation
```

Compile:

```bash
sbt clean
sbt compile
```

Run:

```bash
sbt run
```

## Clean Terminal Output

Spark logging is configured through:

`src/main/resources/log4j2.properties`

The application also uses:

```scala
spark.sparkContext.setLogLevel("ERROR")
```

This hides normal Spark `INFO` and `WARN` messages so the terminal focuses on the application's output and important errors.

## Sample Output

```text
========================================
       DAY 4 - RDD CREATION
========================================

Spark Version : 3.5.3
Application   : Day4RDDCreation
Master        : local[*]

========== 1. RDD FROM COLLECTION ==========
Original Marks
85
72
91
64
55
78
88

Number of Partitions : 12

========== 2. MAP TRANSFORMATION ==========
Marks After Adding Bonus
90
77
96
69
60
83
93

========== 3. FILTER TRANSFORMATION ==========
Passed Students Marks
90
77
96
83
93

========== 4. FLATMAP TRANSFORMATION ==========
Words Generated Using flatMap
Apache
Spark
is
fast
Scala
is
powerful
RDD
supports
distributed
processing

========== 5. RDD FROM TEXT FILE ==========
Sales File Content
Laptop 50000
Mouse 800
Keyboard 1500
Monitor 12000
Laptop 55000
Mouse 1000
Keyboard 1800
Monitor 15000

========== 6. SALES RECORDS ==========
(Laptop,50000.0)
(Mouse,800.0)
(Keyboard,1500.0)
(Monitor,12000.0)
(Laptop,55000.0)
(Mouse,1000.0)
(Keyboard,1800.0)
(Monitor,15000.0)

========== 7. FILTER HIGH VALUE SALES ==========
(Laptop,50000.0)
(Monitor,12000.0)
(Laptop,55000.0)
(Monitor,15000.0)

========== 8. TOTAL SALES ==========
Total Sales Amount : ₹137100.0

========== 9. PRODUCT WISE SALES ==========
(Laptop,105000.0)
(Monitor,27000.0)
(Mouse,1800.0)
(Keyboard,3300.0)

========== 10. PARTITIONS ==========
Sales RDD Partitions : 2
Default Parallelism  : 12

========================================
Day 4 completed successfully!
Aditya completed the RDD Creation Practice.
========================================
```

## Results

### Total Sales

```text
₹137100.0
```

### Product-wise Sales

```text
Laptop   → ₹105000
Monitor  → ₹27000
Mouse    → ₹1800
Keyboard → ₹3300
```

### Partitions

Example output:

```text
Sales RDD Partitions : 2
Default Parallelism  : 12
```
