# Day 8 — DAG and Spark Execution

## Objective

Day 8 focuses on understanding:

- DAG (Directed Acyclic Graph) in Spark
- RDD transformation flow
- RDD lineage
- Shuffle operations
- Spark execution flow
- Stages and tasks
- Partitions and parallelism

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- Java 17.0.20
- SBT
- Ubuntu / WSL2

## Project Structure

```text
day8_DAGSparkExecution/
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
            └── Day8DAGSparkExecution.scala
```

## Input Data

```text
spark scala
spark execution
spark dag
spark stages
spark tasks
scala spark
```

## How to Run

```bash
cd ~/scala-spark-30-days-practice/day8_DAGSparkExecution
sbt compile
sbt run
```

## Complete Output

```text
========================================
DAY 8 - DAG AND SPARK EXECUTION
===============================

Spark Version : 3.5.3
Application   : Day8DAGSparkExecution
Master        : local[*]

========== 1. CREATE INPUT RDD ==========
Input Data:
spark scala
spark execution
spark dag
spark stages
spark tasks
scala spark

========== 2. TRANSFORMATION CHAIN ==========
Transformations created:
textFile
↓
flatMap
↓
filter
↓
map
↓
reduceByKey

========== 3. REDUCE BY KEY ==========
Word Counts:
(dag,1)
(execution,1)
(scala,2)
(spark,6)
(stages,1)
(tasks,1)

========== 4. DAG / RDD LINEAGE ==========
RDD Lineage:
(2) ShuffledRDD[5] at reduceByKey at Day8DAGSparkExecution.scala:64 []
+-(2) MapPartitionsRDD[4] at map at Day8DAGSparkExecution.scala:45 []
|  MapPartitionsRDD[3] at filter at Day8DAGSparkExecution.scala:43 []
|  MapPartitionsRDD[2] at flatMap at Day8DAGSparkExecution.scala:41 []
|  data/input.txt MapPartitionsRDD[1] at textFile at Day8DAGSparkExecution.scala:30 []
|  data/input.txt HadoopRDD[0] at textFile at Day8DAGSparkExecution.scala:30 []

DAG Flow:
Input RDD
↓
flatMap
↓
filter
↓
map
↓
reduceByKey
↓
Action: collect()

========== 5. SPARK EXECUTION FLOW ==========

1. Driver program creates the Spark application.
2. Transformations build the execution plan.
3. An action triggers execution.
4. Spark creates stages from the DAG.
5. Stages are divided into tasks.
6. Tasks execute on available partitions.

========== 6. PARTITIONS ==========
Input RDD Partitions : 2
Word RDD Partitions  : 2
Result RDD Partitions: 2
Default Parallelism  : 12

========== 7. FINAL RESULT ==========
Total unique words: 6

========================================
Day 8 completed successfully!
Aditya completed DAG and Spark Execution.
=========================================

[success] elapsed time: 6 s
```

