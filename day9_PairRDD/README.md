# Day 9 — Pair RDD

## Objective
Practice Pair RDDs and key-value operations:
- Creating Pair RDDs
- `reduceByKey`
- `groupByKey`
- `sortByKey`
- Sorting by value
- Finding highest sales
- Understanding partitions

## Technologies Used
- Scala 2.12.18
- Apache Spark 3.5.3
- Java 17.0.20
- SBT
- Ubuntu / WSL2

## Project Structure
```text
day9_PairRDD/
├── build.sbt
├── .gitignore
├── Output
├── README.md
├── data/
│   └── sales.txt
└── src/main/
    ├── resources/log4j2.properties
    └── scala/Day9PairRDD.scala
```

## Input Data
```text
Laptop 50000
Mouse 800
Laptop 55000
Keyboard 1500
Mouse 1000
Monitor 12000
Keyboard 1800
Monitor 15000
Laptop 60000
Mouse 1200
```


## How to Run
```bash
cd ~/scala-spark-30-days-practice/day9_PairRDD
sbt compile
sbt run
```

## Complete Actual Output
```text
# [info] running (fork) Day9PairRDD

========================================
DAY 9 - PAIR RDD
================

Spark Version : 3.5.3
Application   : Day9PairRDD
Master        : local[*]

========== 1. READ SALES DATA ==========
Sales Data:
Laptop 50000
Mouse 800
Laptop 55000
Keyboard 1500
Mouse 1000
Monitor 12000
Keyboard 1800
Monitor 15000
Laptop 60000
Mouse 1200

========== 2. CREATE PAIR RDD ==========
Pair RDD:
(Laptop,50000.0)
(Mouse,800.0)
(Laptop,55000.0)
(Keyboard,1500.0)
(Mouse,1000.0)
(Monitor,12000.0)
(Keyboard,1800.0)
(Monitor,15000.0)
(Laptop,60000.0)
(Mouse,1200.0)

========== 3. REDUCE BY KEY ==========
Total Sales by Product:
(Keyboard,3300.0)
(Laptop,165000.0)
(Monitor,27000.0)
(Mouse,3000.0)

========== 4. GROUP BY KEY ==========
Sales Values by Product:
Keyboard -> List(1500.0, 1800.0)
Laptop -> List(50000.0, 55000.0, 60000.0)
Monitor -> List(12000.0, 15000.0)
Mouse -> List(800.0, 1000.0, 1200.0)

========== 5. SORT BY KEY ==========
Products Sorted by Name:
(Keyboard,3300.0)
(Laptop,165000.0)
(Monitor,27000.0)
(Mouse,3000.0)

========== 6. SORT BY VALUE ==========
Products Sorted by Total Sales:
(Mouse,3000.0)
(Keyboard,3300.0)
(Monitor,27000.0)
(Laptop,165000.0)

========== 7. HIGHEST SALES ==========
Highest Selling Product:
(Laptop,165000.0)

========== 8. PARTITIONS ==========
Input RDD Partitions : 2
Pair RDD Partitions  : 2
Default Parallelism  : 12

========================================
Day 9 completed successfully!
Aditya completed Pair RDD practice.
===================================

[success] elapsed time: 7 s
```

