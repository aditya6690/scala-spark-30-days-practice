# Day 14 — DataFrame and Dataset

## Objective

The objective of Day 14 is to practice Spark DataFrames and Datasets by building a typed employee payroll pipeline.

The exercise covers:

- Creating a case class and converting a DataFrame to a Dataset
- Converting a Dataset back to a DataFrame
- Comparing RDD, DataFrame, and Dataset
- Understanding type safety
- Understanding Catalyst optimization
- Building a typed employee payroll pipeline

## Technologies

- Apache Spark 3.5.3
- Scala 2.12.18
- Java 17
- SBT
- Spark SQL
- CSV

## Project Structure

```text
day14_DataFrameDataset/
├── build.sbt
├── .gitignore
├── README.md
├── Output
├── data/
│   └── employees.csv
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day14DataFrameDataset.scala
```

## Input Data

The project uses an employee CSV file with the following columns:

| Column | Description |
|---|---|
| `employee_id` | Unique employee identifier |
| `name` | Employee name |
| `department` | Employee department |
| `salary` | Base salary |
| `bonus` | Employee bonus |

The dataset contains 10 employees across Engineering, Finance, HR, and Sales.

## Execution

From the project directory:

```bash
cd ~/scala-spark-30-days-practice/day14_DataFrameDataset
```

Compile:

```bash
sbt compile
```

Run:

```bash
sbt run
```

## Expected Output

After running:

```bash
sbt run
```

the console should produce output similar to the following. Formatting and spacing may vary slightly depending on the Spark/SBT environment.

```text
==============================================
DAY 14 - DATAFRAME AND DATASET
==============================================

--- Employee DataFrame ---
+-----------+------+-----------+------+-----+
|employee_id|name  |department |salary|bonus|
+-----------+------+-----------+------+-----+
|E001       |Aditya|Engineering|60000 |8000 |
|E002       |Rahul |Engineering|55000 |6000 |
|E003       |Priya |HR         |48000 |5000 |
|E004       |Neha  |Finance    |70000 |9000 |
|E005       |Arjun |Engineering|65000 |7500 |
|E006       |Ananya|HR         |52000 |5500 |
|E007       |Rohan |Finance    |62000 |7000 |
|E008       |Sneha |Sales      |45000 |6000 |
|E009       |Vikram|Sales      |50000 |6500 |
|E010       |Kavya |Engineering|58000 |7000 |
+-----------+------+-----------+------+-----+

--- DataFrame Schema ---
root
 |-- employee_id: string (nullable = true)
 |-- name: string (nullable = true)
 |-- department: string (nullable = true)
 |-- salary: integer (nullable = true)
 |-- bonus: integer (nullable = true)

--- Typed Employee Payroll Dataset ---
+-----------+------+-----------+-------+------+-------------+
|employee_id|name  |department |salary |bonus |total_payroll|
+-----------+------+-----------+-------+------+-------------+
|E001       |Aditya|Engineering|60000.0|8000.0|68000.0      |
|E002       |Rahul |Engineering|55000.0|6000.0|61000.0      |
|E003       |Priya |HR         |48000.0|5000.0|53000.0      |
|E004       |Neha  |Finance    |70000.0|9000.0|79000.0      |
|E005       |Arjun |Engineering|65000.0|7500.0|72500.0      |
|E006       |Ananya|HR         |52000.0|5500.0|57500.0      |
|E007       |Rohan |Finance    |62000.0|7000.0|69000.0      |
|E008       |Sneha |Sales      |45000.0|6000.0|51000.0      |
|E009       |Vikram|Sales      |50000.0|6500.0|56500.0      |
|E010       |Kavya |Engineering|58000.0|7000.0|65000.0      |
+-----------+------+-----------+-------+------+-------------+

--- Department-wise Payroll ---
+-----------+---------+------------+-----------+-------------+---------------+
|department |employees|total_salary|total_bonus|total_payroll|average_payroll|
+-----------+---------+------------+-----------+-------------+---------------+
|Engineering|4        |238000.0    |28500.0    |266500.0     |66625.0        |
|Finance    |2        |132000.0    |16000.0    |148000.0     |74000.0        |
|HR         |2        |100000.0    |10500.0    |110500.0     |55250.0        |
|Sales      |2        |95000.0     |12500.0    |107500.0     |53750.0        |
+-----------+---------+------------+-----------+-------------+---------------+

--- Overall Payroll Statistics ---
+---------------+------------+-----------+-------------+---------------+---------------+---------------+
|total_employees|total_salary|total_bonus|total_payroll|average_payroll|maximum_payroll|minimum_payroll|
+---------------+------------+-----------+-------------+---------------+---------------+---------------+
|10             |565000.0    |67500.0    |632500.0     |63250.0        |79000.0        |51000.0        |
+---------------+------------+-----------+-------------+---------------+---------------+---------------+

==============================================
RDD vs DATAFRAME vs DATASET
==============================================

RDD:
- Low-level distributed collection.
- Provides fine-grained control over data.
- Does not provide DataFrame/Dataset schema information.

DataFrame:
- Distributed collection organized into named columns.
- DataFrame is effectively Dataset[Row].
- Supports Spark SQL and Catalyst query optimization.

Dataset:
- Combines structured data processing with strong typing.
- Uses case classes for typed records.
- Provides compile-time type checking for typed operations.

==============================================
PAYROLL PIPELINE SUMMARY
==============================================
Input Format       : CSV
DataFrame Created  : Yes
Dataset Created    : Yes
Dataset -> DF      : Yes
Typed Payroll      : Yes
Department Report  : Yes
Overall Report     : Yes

==============================================
Day 14 execution completed successfully.
==============================================
[success] elapsed time: 7 s
```
