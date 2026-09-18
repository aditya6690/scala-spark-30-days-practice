# Day 3 – Spark Setup and First Application

## Objective

The objective of Day 3 is to create the first Scala + Apache Spark application using SBT.

This exercise introduces SparkSession, SparkContext, reading a text file, and the basic roles of the Driver, Executor, and Cluster Manager.

The application is also tested in local mode using 2 and 4 CPU cores.

## Topics Covered

- Scala Spark project with SBT
- SparkSession
- SparkContext
- Reading a text file
- Displaying file contents
- Driver
- Executor
- Cluster Manager
- Local mode
- Running with 2 cores
- Running with 4 cores

## Scenario

Build a simple Spark application that reads a text file and displays its contents.

The same application is executed using different local CPU configurations.

## Project Structure

```text
day3_SparkSetup/
├── build.sbt
├── Output
├── README.md
├── .gitignore
├── data/
│   └── sample.txt
└── src/
    └── main/
        └── scala/
            └── Day3SparkSetup.scala
```

## Technologies Used

- Scala 2.12.18
- Apache Spark 3.5.3
- SBT
- Java 17

## Spark Dependencies

The project uses:

- spark-core 3.5.3
- spark-sql 3.5.3

Both are used with Scala 2.12.

## Sample Input

`data/sample.txt`

```text
Apache Spark is a distributed data processing framework.
Spark can process large datasets efficiently.
Scala is commonly used to write Spark applications.
Spark applications can run in local mode during development.
```

## How to Run

Navigate to the project:

```bash
cd ~/scala-spark-practice/day3_SparkSetup
```

Compile:

```bash
sbt clean
sbt compile
```

Run using all available cores:

```bash
sbt run
```

Run using 2 local cores:

```bash
sbt "run 2"
```

Run using 4 local cores:

```bash
sbt "run 4"
```

## Expected Output

```text
========================================
       DAY 3 - SPARK SETUP
========================================

Spark Version: 3.5.3
Application Name: Day3SparkSetup
Master: local[*]

--- Spark Context ---
Application ID: local-xxxxxxxxxxxxxxxx
Default Parallelism: ...

--- Reading Text File ---

Apache Spark is a distributed data processing framework.
Spark can process large datasets efficiently.
Scala is commonly used to write Spark applications.
Spark applications can run in local mode during development.

Number of Lines: 4

========================================
Day 3 completed successfully!
Spark application executed successfully.
========================================
```

When running with 2 cores:

```text
Master: local[2]
```

When running with 4 cores:

```text
Master: local[4]
```

## Spark Architecture

### Driver

The Driver is the main process that runs the Spark application.

Responsibilities:

- Creates the SparkSession
- Creates/accesses SparkContext
- Builds the execution plan
- Coordinates Spark work

### Executor

Executors perform tasks assigned by the Driver.

They:

- Execute tasks
- Process data
- Store cached data when required
- Return results to the Driver

### Cluster Manager

A cluster manager provides resources to Spark applications.

Common cluster managers:

- Standalone
- YARN
- Kubernetes

For this exercise, Spark runs in local mode, so a separate cluster manager is not required.

## Local Mode

Local mode runs Spark on the same machine where the application is started.

```text
local[2]  -> use 2 CPU cores
local[4]  -> use 4 CPU cores
local[*]  -> use all available logical cores
```