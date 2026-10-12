# Day 15 — UDF Practice

## Objective

Practice User Defined Functions (UDFs) in Apache Spark with Scala:
- Create a Scala UDF to classify customer spending bands.
- Add calculated columns using `withColumn`.
- Compare a UDF with built-in Spark functions.
- Register a UDF and call it from Spark SQL.
- Create customer risk categories from transaction data.

## Technologies

- Apache Spark 3.5.3
- Scala 2.12.18
- Java 17
- SBT
- Spark SQL
- CSV

## Project Structure

```text
day15_UDFPractice/
├── build.sbt
├── .gitignore
├── README.md
├── Output
├── data/
│   └── customers.csv
└── src/main/
    ├── resources/log4j2.properties
    └── scala/Day15UDFPractice.scala
```

## Input Data

The program reads `data/customers.csv` with 10 customer records.

| Column | Description |
|---|---|
| `customer_id` | Unique customer identifier |
| `name` | Customer name |
| `age` | Customer age |
| `total_transactions` | Number of transactions |
| `total_spend` | Total customer spending |

## Complete Console Output

The following is the full console output in the format provided for the successful Day 15 run:

```text
[info] running (fork) Day15UDFPractice

==============================================
DAY 15 - UDF PRACTICE
==============================================

--- Customer DataFrame ---
+-----------+------+---+------------------+-----------+
|customer_id|name  |age|total_transactions|total_spend|
+-----------+------+---+------------------+-----------+
|C001       |Aditya|22 |12                |45000      |
|C002       |Rahul |28 |8                 |62000      |
|C003       |Priya |25 |5                 |18000      |
|C004       |Neha  |31 |20                |95000      |
|C005       |Arjun |27 |15                |72000      |
|C006       |Ananya|24 |3                 |12000      |
|C007       |Rohan |35 |25                |125000     |
|C008       |Sneha |29 |10                |55000      |
|C009       |Vikram|41 |30                |150000     |
|C010       |Kavya |23 |4                 |22000      |
+-----------+------+---+------------------+-----------+


--- Customer Schema ---
root
 |-- customer_id: string (nullable = true)
 |-- name: string (nullable = true)
 |-- age: integer (nullable = true)
 |-- total_transactions: integer (nullable = true)
 |-- total_spend: integer (nullable = true)


--- Salary Band Using Scala UDF ---
+-----------+------+-----------+-----------+
|customer_id|name  |total_spend|salary_band|
+-----------+------+-----------+-----------+
|C001       |Aditya|45000      |Bronze     |
|C002       |Rahul |62000      |Silver     |
|C003       |Priya |18000      |Bronze     |
|C004       |Neha  |95000      |Gold       |
|C005       |Arjun |72000      |Silver     |
|C006       |Ananya|12000      |Bronze     |
|C007       |Rohan |125000     |Platinum   |
|C008       |Sneha |55000      |Silver     |
|C009       |Vikram|150000     |Platinum   |
|C010       |Kavya |22000      |Bronze     |
+-----------+------+-----------+-----------+


--- Salary Band Using Built-in Spark Functions ---
+-----------+------+-----------+-------------------+
|customer_id|name  |total_spend|salary_band_builtin|
+-----------+------+-----------+-------------------+
|C001       |Aditya|45000      |Bronze             |
|C002       |Rahul |62000      |Silver             |
|C003       |Priya |18000      |Bronze             |
|C004       |Neha  |95000      |Gold               |
|C005       |Arjun |72000      |Silver             |
|C006       |Ananya|12000      |Bronze             |
|C007       |Rohan |125000     |Platinum           |
|C008       |Sneha |55000      |Silver             |
|C009       |Vikram|150000     |Platinum           |
|C010       |Kavya |22000      |Bronze             |
+-----------+------+-----------+-------------------+


--- UDF vs Built-in Function Comparison ---
+-----------+------+-----------+--------+------------+-----+
|customer_id|name  |total_spend|udf_band|builtin_band|match|
+-----------+------+-----------+--------+------------+-----+
|C001       |Aditya|45000      |Bronze  |Bronze      |true |
|C002       |Rahul |62000      |Silver  |Silver      |true |
|C003       |Priya |18000      |Bronze  |Bronze      |true |
|C004       |Neha  |95000      |Gold    |Gold        |true |
|C005       |Arjun |72000      |Silver  |Silver      |true |
|C006       |Ananya|12000      |Bronze  |Bronze      |true |
|C007       |Rohan |125000     |Platinum|Platinum    |true |
|C008       |Sneha |55000      |Silver  |Silver      |true |
|C009       |Vikram|150000     |Platinum|Platinum    |true |
|C010       |Kavya |22000      |Bronze  |Bronze      |true |
+-----------+------+-----------+--------+------------+-----+


--- Registered UDF Using Spark SQL ---
+-----------+------+-----------+-----------+
|customer_id|name  |total_spend|salary_band|
+-----------+------+-----------+-----------+
|C009       |Vikram|150000     |Platinum   |
|C007       |Rohan |125000     |Platinum   |
|C004       |Neha  |95000      |Gold       |
|C005       |Arjun |72000      |Silver     |
|C002       |Rahul |62000      |Silver     |
|C008       |Sneha |55000      |Silver     |
|C001       |Aditya|45000      |Bronze     |
|C010       |Kavya |22000      |Bronze     |
|C003       |Priya |18000      |Bronze     |
|C006       |Ananya|12000      |Bronze     |
+-----------+------+-----------+-----------+


--- Customer Risk Category ---
+-----------+------+------------------+-----------+-------------+
|customer_id|name  |total_transactions|total_spend|risk_category|
+-----------+------+------------------+-----------+-------------+
|C009       |Vikram|30                |150000     |High Risk    |
|C007       |Rohan |25                |125000     |High Risk    |
|C004       |Neha  |20                |95000      |Medium Risk  |
|C005       |Arjun |15                |72000      |Medium Risk  |
|C002       |Rahul |8                 |62000      |Medium Risk  |
|C008       |Sneha |10                |55000      |Medium Risk  |
|C001       |Aditya|12                |45000      |Medium Risk  |
|C010       |Kavya |4                 |22000      |Low Risk     |
|C003       |Priya |5                 |18000      |Low Risk     |
|C006       |Ananya|3                 |12000      |Low Risk     |
+-----------+------+------------------+-----------+-------------+


--- Risk Category Summary ---
+-------------+-----+
|risk_category|count|
+-------------+-----+
|High Risk    |2    |
|Low Risk     |3    |
|Medium Risk  |5    |
+-------------+-----+


==============================================
UDF PRACTICE SUMMARY
==============================================
Scala UDF Created          : Yes
withColumn Used            : Yes
Built-in Function Used     : Yes
UDF vs Built-in Compared   : Yes
UDF Registered in Catalog  : Yes
Spark SQL UDF Used         : Yes
Customer Risk UDF          : Yes

==============================================
Day 15 execution completed successfully.
==============================================
[success] elapsed time: 8 s
```
