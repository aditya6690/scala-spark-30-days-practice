# Day 13 — Spark SQL Basics

## Objective

Day 13 focuses on Spark SQL basics:

- Create a DataFrame from CSV data.
- Inspect the DataFrame schema.
- Select and filter columns/rows.
- Use `withColumn` and expressions.
- Register a temporary view and run SQL.
- Build a customer analytics report using Spark SQL.

## Technologies

- Scala 2.12.18
- Apache Spark 3.5.3
- Spark Core
- Spark SQL
- SBT
- Java 17
- Linux / WSL2

## Project Structure

```text
day13_SparkSQLBasics/
├── build.sbt
├── .gitignore
├── README.md
├── Output
├── data/
│   └── customers.csv
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day13SparkSQLBasics.scala
```

## Input Data

File:

```text
data/customers.csv
```

Contents:

```csv
customer_id,name,city,age,total_spend
C001,Aditya,Hyderabad,22,45000
C002,Rahul,Bangalore,28,62000
C003,Priya,Chennai,25,38000
C004,Neha,Delhi,31,75000
C005,Arjun,Mumbai,27,54000
C006,Ananya,Pune,24,29000
C007,Rohan,Hyderabad,35,88000
C008,Sneha,Delhi,29,47000
C009,Vikram,Mumbai,41,95000
C010,Kavya,Chennai,23,33000
```

## Running the Project

Navigate to the project:

```bash
cd ~/scala-spark-30-days-practice/day13_SparkSQLBasics
```

Run:

```bash
sbt run
```

## Actual Successful Output

```text
==============================================
          DAY 13 - SPARK SQL BASICS
==============================================

Customer DataFrame:
+-----------+------+---------+---+-----------+
|customer_id|  name|     city|age|total_spend|
+-----------+------+---------+---+-----------+
|       C001|Aditya|Hyderabad| 22|      45000|
|       C002| Rahul|Bangalore| 28|      62000|
|       C003| Priya|  Chennai| 25|      38000|
|       C004|  Neha|    Delhi| 31|      75000|
|       C005| Arjun|   Mumbai| 27|      54000|
|       C006|Ananya|     Pune| 24|      29000|
|       C007| Rohan|Hyderabad| 35|      88000|
|       C008| Sneha|    Delhi| 29|      47000|
|       C009|Vikram|   Mumbai| 41|      95000|
|       C010| Kavya|  Chennai| 23|      33000|
+-----------+------+---------+---+-----------+

DataFrame Schema:
root
 |-- customer_id: string (nullable = true)
 |-- name: string (nullable = true)
 |-- city: string (nullable = true)
 |-- age: integer (nullable = true)
 |-- total_spend: integer (nullable = true)

Selected Columns:
+-----------+------+---------+-----------+
|customer_id|  name|     city|total_spend|
+-----------+------+---------+-----------+
|       C001|Aditya|Hyderabad|      45000|
|       C002| Rahul|Bangalore|      62000|
|       C003| Priya|  Chennai|      38000|
|       C004|  Neha|    Delhi|      75000|
|       C005| Arjun|   Mumbai|      54000|
|       C006|Ananya|     Pune|      29000|
|       C007| Rohan|Hyderabad|      88000|
|       C008| Sneha|    Delhi|      47000|
|       C009|Vikram|   Mumbai|      95000|
|       C010| Kavya|  Chennai|      33000|
+-----------+------+---------+-----------+

Customers with Spend Greater Than 50000:
+-----------+------+---------+-----------+
|customer_id|  name|     city|total_spend|
+-----------+------+---------+-----------+
|       C002| Rahul|Bangalore|      62000|
|       C004|  Neha|    Delhi|      75000|
|       C005| Arjun|   Mumbai|      54000|
|       C007| Rohan|Hyderabad|      88000|
|       C009|Vikram|   Mumbai|      95000|
+-----------+------+---------+-----------+

Customer Segmentation:
+-----------+------+---------+-----------+----------------+
|customer_id|  name|     city|total_spend|customer_segment|
+-----------+------+---------+-----------+----------------+
|       C001|Aditya|Hyderabad|      45000|           Basic|
|       C002| Rahul|Bangalore|      62000|         Regular|
|       C003| Priya|  Chennai|      38000|           Basic|
|       C004|  Neha|    Delhi|      75000|         Regular|
|       C005| Arjun|   Mumbai|      54000|         Regular|
|       C006|Ananya|     Pune|      29000|           Basic|
|       C007| Rohan|Hyderabad|      88000|         Premium|
|       C008| Sneha|    Delhi|      47000|           Basic|
|       C009|Vikram|   Mumbai|      95000|         Premium|
|       C010| Kavya|  Chennai|      33000|           Basic|
+-----------+------+---------+-----------+----------------+

Temporary View 'customers' created successfully.

SQL Report - Premium Customers:
+-----------+------+---------+-----------+----------------+
|customer_id|  name|     city|total_spend|customer_segment|
+-----------+------+---------+-----------+----------------+
|       C009|Vikram|   Mumbai|      95000|         Premium|
|       C007| Rohan|Hyderabad|      88000|         Premium|
+-----------+------+---------+-----------+----------------+

SQL Report - City-wise Customer Analytics:
+---------+--------------+-------------+----------------+
|     city|customer_count|average_spend|total_city_spend|
+---------+--------------+-------------+----------------+
|   Mumbai|             2|      74500.0|          149000|
|Hyderabad|             2|      66500.0|          133000|
|    Delhi|             2|      61000.0|          122000|
|  Chennai|             2|      35500.0|           71000|
|Bangalore|             1|      62000.0|           62000|
|     Pune|             1|      29000.0|           29000|
+---------+--------------+-------------+----------------+

SQL Report - Overall Customer Analytics:
+---------------+-------------+-----------+-------------+-------------+
|total_customers|average_spend|total_spend|maximum_spend|minimum_spend|
+---------------+-------------+-----------+-------------+-------------+
|             10|      56600.0|     566000|        95000|        29000|
+---------------+-------------+-----------+-------------+-------------+

Day 13 Concepts Demonstrated:
----------------------------------------------
1. DataFrame created from CSV
2. Schema inspection using printSchema()
3. Column selection using select()
4. Row filtering using filter()
5. Calculated column using withColumn()
6. Temporary view using createOrReplaceTempView()
7. SQL queries using spark.sql()
8. Customer analytics using GROUP BY and aggregate functions

==============================================
Day 13 execution completed successfully.
==============================================
[success] elapsed time: 8 s
```