# Day 5 – Spark Transformations and Actions

## Objective

The objective of Day 5 is to understand Spark RDD transformations and actions, lazy evaluation, and the basic execution flow of a Spark application.

A practical Log Analyzer is also implemented using RDD operations.

## Topics Covered

- RDD creation
- `map`
- `filter`
- `flatMap`
- `distinct`
- `union`
- `count`
- `collect`
- `take`
- `first`
- `reduce`
- Lazy evaluation
- Transformations vs Actions
- Spark execution flow
- RDD partitions
- Default parallelism
- Log Analyzer

## Project Structure

```text
day5_TransformationsActions/
├── build.sbt
├── .gitignore
├── README.md
├── data/
│   └── application.log
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day5TransformationsActions.scala
```

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- Spark Core
- Spark SQL
- SBT
- Java 17
- WSL2 / Ubuntu

## Log Analyzer

The project reads:

```text
data/application.log
```

Example:

```text
INFO Application started
INFO User login successful
ERROR Database connection failed
INFO User login successful
WARN High memory usage
INFO Payment processed
ERROR Payment failed
INFO User logout
INFO User login successful
WARN High memory usage
ERROR Database connection failed
INFO Application stopped
```

The application analyzes:

- Total log lines
- ERROR logs
- WARN logs
- INFO logs
- Actual ERROR messages
- Actual WARN messages

## How to Run

Navigate to the project:

```bash
cd ~/scala-spark-practice/day5_TransformationsActions
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

## Expected Output

After running `sbt run`, the output should be approximately:

```text
========================================
   DAY 5 - TRANSFORMATIONS & ACTIONS
========================================
Spark Version : 3.5.3
Application   : Day5TransformationsActions
Master        : local[*]

========== 1. CREATE RDD ==========
Original RDD:
1
2
3
4
5
5
6
7
8
8

========== 2. MAP ==========
Squared Numbers:
1
4
9
16
25
25
36
49
64
64

========== 3. FILTER ==========
Even Numbers:
2
4
6
8
8

========== 4. FLATMAP ==========
Words:
Spark
is
fast
Scala
is
powerful
RDD
is
distributed

========== 5. DISTINCT ==========
Distinct Numbers:
1
2
3
4
5
6
7
8

========== 6. UNION ==========
Combined RDD:
10
20
30
40
50
60

========== 7. COUNT ==========
Number of Elements : 10

========== 8. TAKE ==========
First 3 Elements:
1
2
3

========== 9. FIRST ==========
First Number : 1

========== 10. REDUCE ==========
Sum of All Numbers : 49

========== 11. LOG ANALYZER ==========
Total Log Lines : 12
ERROR Logs : 3
WARN Logs : 2
INFO Logs : 7

========== 12. LAZY EVALUATION ==========
Transformation created.
No execution happens until an action is called.
Calling collect() now:
40
50
60
70
80
80

========== 13. PARTITIONS ==========
Number RDD Partitions : 12
Log RDD Partitions    : 2
Default Parallelism   : 12

========================================
Day 5 completed successfully!
Aditya completed the Transformations and Actions practice.
========================================
```