# Day 6 — Word Count using Spark RDD

## Objective

Implement a Word Count application using Apache Spark RDDs and understand how text data is transformed into key-value pairs and aggregated.

## Topics Covered

- Reading text files using `textFile()`
- Creating RDDs
- `flatMap()` transformation
- `map()` transformation
- Key-value pairs
- `reduceByKey()`
- `sortBy()`
- RDD partitions
- Spark transformations and actions
- Basic Word Count processing

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- Java 17
- SBT
- Ubuntu WSL2

## Project Structure

```text
day6_WordCount/
├── build.sbt
├── Output
├── .gitignore
├── README.md
├── data/
│   └── input.txt
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day6WordCount.scala
```

## Input Data

```text
Apache Spark is fast
Spark is powerful
Scala and Spark are popular
Spark supports big data processing
Scala is used with Spark
```

## Word Count Process

```text
Input Text
    ↓
textFile()
    ↓
flatMap()
    ↓
map(word => (word, 1))
    ↓
reduceByKey()
    ↓
sortBy()
    ↓
Word Count
```

### 1. Read Text File

```scala
val lines = sc.textFile("data/input.txt")
```

### 2. flatMap

```scala
val words = lines.flatMap(line => line.split(" "))
```

### 3. Convert Words to Key-Value Pairs

```scala
val wordPairs = words.map(word => (word.toLowerCase, 1))
```

Example:

```text
Spark → (spark,1)
Scala → (scala,1)
```

### 4. reduceByKey

```scala
val wordCounts = wordPairs.reduceByKey((a, b) => a + b)
```

For example:

```text
(spark,1)
(spark,1)
(spark,1)
(spark,1)
(spark,1)
```

becomes:

```text
(spark,5)
```

### 5. Sort Word Counts

```scala
val sortedWordCounts =
  wordCounts.sortBy(_._2, ascending = false)
```

## How to Run

```bash
cd ~/scala-spark-30-days-practice/day6_WordCount
sbt clean
sbt compile
sbt run
```

## Expected Output

After running `sbt run`, the successful output was:

```text
========================================
       DAY 6 - WORD COUNT
========================================

Spark Version : 3.5.3
Application   : Day6WordCount
Master        : local[*]

========== 1. READ TEXT FILE ==========
Input Data:
Apache Spark is fast
Spark is powerful
Scala and Spark are popular
Spark supports big data processing
Scala is used with Spark

========== 2. FLATMAP ==========
Words:
Apache
Spark
is
fast
Spark
is
powerful
Scala
and
Spark
are
popular
Spark
supports
big
data
processing
Scala
is
used
with
Spark

========== 3. MAP TO KEY-VALUE ==========
Word Pairs:
(apache,1)
(spark,1)
(is,1)
(fast,1)
(spark,1)
(is,1)
(powerful,1)
(scala,1)
(and,1)
(spark,1)
(are,1)
(popular,1)
(spark,1)
(supports,1)
(big,1)
(data,1)
(processing,1)
(scala,1)
(is,1)
(used,1)
(with,1)
(spark,1)

========== 4. REDUCE BY KEY ==========
Word Counts:
(and,1)
(apache,1)
(are,1)
(big,1)
(data,1)
(fast,1)
(is,3)
(popular,1)
(powerful,1)
(processing,1)
(scala,2)
(spark,5)
(supports,1)
(used,1)
(with,1)

========== 5. SORT WORD COUNTS ==========
Words Sorted by Count:
(spark,5)
(is,3)
(scala,2)
(are,1)
(fast,1)
(big,1)
(with,1)
(data,1)
(apache,1)
(supports,1)
(powerful,1)
(popular,1)
(processing,1)
(used,1)
(and,1)

========== 6. PARTITIONS ==========
Input RDD Partitions : 2
Words RDD Partitions : 2
Default Parallelism  : 12

========================================
Day 6 completed successfully!
Aditya completed the Word Count practice.
========================================

[success] elapsed time: 5 s
```

## Results

| Metric | Result |
|---|---:|
| Input Lines | 5 |
| Total Words | 22 |
| Unique Words | 15 |
| `spark` Count | 5 |
| `is` Count | 3 |
| `scala` Count | 2 |
| `with` Count | 1 |
| Input RDD Partitions | 2 |
| Words RDD Partitions | 2 |
| Default Parallelism | 12 |


