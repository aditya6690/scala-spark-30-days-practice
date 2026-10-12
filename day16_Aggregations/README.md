# Day 16 – Aggregations (Scala + Apache Spark)

## Objective

Practice Spark DataFrame aggregation functions and apply them to a hospital department revenue scenario.

## Concepts Covered

- `count()`, `sum()`, `avg()`, `min()`, and `max()`
- Department-wise aggregation using `groupBy()`
- Grouping by multiple columns (`department`, `doctor`)
- HAVING-like filtering after aggregation
- Department revenue and patient metrics
- Calculating each department's percentage contribution to total revenue

## Project Structure

```text
day16_Aggregations/
├── build.sbt
├── Output
├── README.md
├── data/
│   └── hospital_revenue.csv
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day16Aggregations.scala
```

## Dataset

The file `data/hospital_revenue.csv` contains hospital department, doctor, revenue, and patient-count records. It has 12 doctor records across Cardiology, Neurology, Orthopedics, and Pediatrics.

## How to Run

From the `day16_Aggregations` directory:

```bash
sbt compile
sbt run
```

## Key Results

| Metric | Result |
|---|---:|
| Doctor records | 12 |
| Total revenue | 1,055,000 |
| Average revenue per doctor record | 87,916.67 |
| Minimum revenue | 45,000 |
| Maximum revenue | 150,000 |
| Total patients | 241 |
| Highest-revenue department | Cardiology |
| Cardiology revenue | 360,000 |

## Complete Console Output

```text
==============================================
DAY 16 - AGGREGATIONS
==============================================

--- Hospital Revenue Data ---
+-----------+----------+-------+--------+
|department |doctor    |revenue|patients|
+-----------+----------+-------+--------+
|Cardiology |Dr. Sharma|150000 |30      |
|Cardiology |Dr. Mehta |120000 |25      |
|Cardiology |Dr. Rao   |90000  |20      |
|Neurology  |Dr. Singh |110000 |22      |
|Neurology  |Dr. Patel |95000  |18      |
|Neurology  |Dr. Kumar |85000  |16      |
|Orthopedics|Dr. Verma |100000 |24      |
|Orthopedics|Dr. Das   |80000  |20      |
|Orthopedics|Dr. Reddy |70000  |15      |
|Pediatrics |Dr. Gupta |60000  |20      |
|Pediatrics |Dr. Nair  |50000  |17      |
|Pediatrics |Dr. Khan  |45000  |14      |
+-----------+----------+-------+--------+


--- Hospital Data Schema ---
root
 |-- department: string (nullable = true)
 |-- doctor: string (nullable = true)
 |-- revenue: integer (nullable = true)
 |-- patients: integer (nullable = true)


--- Overall Revenue Statistics ---
+--------------+-------------+---------------+---------------+---------------+--------------+
|doctor_records|total_revenue|average_revenue|minimum_revenue|maximum_revenue|total_patients|
+--------------+-------------+---------------+---------------+---------------+--------------+
|12            |1055000      |87916.67       |45000          |150000         |241           |
+--------------+-------------+---------------+---------------+---------------+--------------+--------------+


--- Department-wise Revenue Statistics ---
+-----------+------------+-------------+---------------+---------------+---------------+--------------+
|department |doctor_count|total_revenue|average_revenue|minimum_revenue|maximum_revenue|total_patients|
+-----------+------------+-------------+---------------+---------------+---------------+--------------+
|Cardiology |3           |360000       |120000.0       |90000          |150000         |75            |
|Neurology  |3           |290000       |96666.67       |85000          |110000         |56            |
|Orthopedics|3           |250000       |83333.33       |70000          |100000         |59            |
|Pediatrics |3           |155000       |51666.67       |45000          |60000          |51            |
+-----------+------------+-------------+---------------+---------------+---------------+--------------+


--- Group by Department and Doctor ---
+-----------+----------+--------------+--------+
|department |doctor    |doctor_revenue|patients|
+-----------+----------+--------------+--------+
|Cardiology |Dr. Mehta |120000        |25      |
|Cardiology |Dr. Rao   |90000         |20      |
|Cardiology |Dr. Sharma|150000        |30      |
|Neurology  |Dr. Kumar |85000         |16      |
|Neurology  |Dr. Patel |95000         |18      |
|Neurology  |Dr. Singh |110000        |22      |
|Orthopedics|Dr. Das   |80000         |20      |
|Orthopedics|Dr. Reddy |70000         |15      |
|Orthopedics|Dr. Verma |100000        |24      |
|Pediatrics |Dr. Gupta |60000         |20      |
|Pediatrics |Dr. Khan  |45000         |14      |
|Pediatrics |Dr. Nair  |50000         |17      |
+-----------+----------+--------------+--------+


--- Departments with Revenue >= 250000 ---
+-----------+------------+-------------+---------------+---------------+---------------+--------------+
|department |doctor_count|total_revenue|average_revenue|minimum_revenue|maximum_revenue|total_patients|
+-----------+------------+-------------+---------------+---------------+---------------+--------------+
|Cardiology |3           |360000       |120000.0       |90000          |150000         |75            |
|Neurology  |3           |290000       |96666.67       |85000          |110000         |56            |
|Orthopedics|3           |250000       |83333.33       |70000          |100000         |59            |
+-----------+------------+-------------+---------------+---------------+---------------+--------------+


--- Department Revenue Contribution ---
+-----------+-------------+------------------+
|department |total_revenue|revenue_percentage|
+-----------+-------------+------------------+
|Cardiology |360000       |34.12             |
|Neurology  |290000       |27.49             |
|Orthopedics|250000       |23.7              |
|Pediatrics |155000       |14.69             |
+-----------+-------------+------------------+


==============================================
AGGREGATION PRACTICE SUMMARY
==============================================
count, sum, avg, min, max Used : Yes
groupBy Used                   : Yes
Multiple-column groupBy Used   : Yes
HAVING-like filter Used        : Yes
Department revenue metrics     : Yes

==============================================
Day 16 execution completed successfully.
==============================================
[success] elapsed time: 8 s
```