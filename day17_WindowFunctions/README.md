# Day 17 — Window Functions

## Objective

Practice Spark SQL window functions in Scala using student scores and customer policy history.

## Topics Covered

- `row_number()`
- `rank()`
- `dense_rank()`
- Window partitioning with `partitionBy`
- Ordering within a window
- Selecting the top 3 students per course
- `lag()` and `lead()`
- Selecting the latest policy record per customer

## Project Structure

```text
day17_WindowFunctions/
├── build.sbt
├── Output
├── README.md
├── data/
│   ├── student_scores.csv
│   └── policy_history.csv
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day17WindowFunctions.scala
```

## Dataset 1: Student Scores

File: `data/student_scores.csv`

```csv
course,student,score
Scala,Aarav,95
Scala,Diya,90
Scala,Rohan,90
Scala,Meera,85
Scala,Kiran,80
Spark,Aarav,98
Spark,Diya,92
Spark,Rohan,92
Spark,Meera,88
Spark,Kiran,82
Python,Aarav,91
Python,Diya,91
Python,Rohan,86
Python,Meera,84
Python,Kiran,78
```

## Dataset 2: Policy History

File: `data/policy_history.csv`

```csv
customer_id,policy_id,policy_type,premium,updated_at
C101,P001,Health,12000,2025-01-10
C101,P002,Life,18000,2025-06-15
C101,P003,Health,15000,2026-02-20
C102,P004,Vehicle,9000,2025-03-12
C102,P005,Vehicle,11000,2026-01-05
C103,P006,Life,22000,2025-08-01
C103,P007,Life,25000,2026-04-18
C104,P008,Health,14000,2026-03-22
```

## Complete Console Output

```text
==================================================
       DAY 17 - WINDOW FUNCTIONS
==================================================

===== STUDENT SCORES =====
+------+-------+-----+
|course|student|score|
+------+-------+-----+
|Scala |Aarav  |95   |
|Scala |Diya   |90   |
|Scala |Rohan  |90   |
|Scala |Meera  |85   |
|Scala |Kiran  |80   |
|Spark |Aarav  |98   |
|Spark |Diya   |92   |
|Spark |Rohan  |92   |
|Spark |Meera  |88   |
|Spark |Kiran  |82   |
|Python|Aarav  |91   |
|Python|Diya   |91   |
|Python|Rohan  |86   |
|Python|Meera  |84   |
|Python|Kiran  |78   |
+------+-------+-----+


===== ROW_NUMBER, RANK, DENSE_RANK =====
+------+-------+-----+----------+----+----------+
|course|student|score|row_number|rank|dense_rank|
+------+-------+-----+----------+----+----------+
|Python|Aarav  |91   |1         |1   |1         |
|Python|Diya   |91   |2         |1   |1         |
|Python|Rohan  |86   |3         |3   |2         |
|Python|Meera  |84   |4         |4   |3         |
|Python|Kiran  |78   |5         |5   |4         |
|Scala |Aarav  |95   |1         |1   |1         |
|Scala |Diya   |90   |2         |2   |2         |
|Scala |Rohan  |90   |3         |2   |2         |
|Scala |Meera  |85   |4         |4   |3         |
|Scala |Kiran  |80   |5         |5   |4         |
|Spark |Aarav  |98   |1         |1   |1         |
|Spark |Diya   |92   |2         |2   |2         |
|Spark |Rohan  |92   |3         |2   |2         |
|Spark |Meera  |88   |4         |4   |3         |
|Spark |Kiran  |82   |5         |5   |4         |
+------+-------+-----+----------+----+----------+


===== TOP 3 STUDENTS PER COURSE =====
+------+-------+-----+----------+----+----------+
|course|student|score|row_number|rank|dense_rank|
+------+-------+-----+----------+----+----------+
|Python|Aarav  |91   |1         |1   |1         |
|Python|Diya   |91   |2         |1   |1         |
|Python|Rohan  |86   |3         |3   |2         |
|Scala |Aarav  |95   |1         |1   |1         |
|Scala |Diya   |90   |2         |2   |2         |
|Scala |Rohan  |90   |3         |2   |2         |
|Spark |Aarav  |98   |1         |1   |1         |
|Spark |Diya   |92   |2         |2   |2         |
|Spark |Rohan  |92   |3         |2   |2         |
+------+-------+-----+----------+----+----------+


===== LAG AND LEAD: STUDENT SCORES =====
+------+-------+-----+--------------+----------+
|course|student|score|previous_score|next_score|
+------+-------+-----+--------------+----------+
|Python|Aarav  |91   |NULL          |91        |
|Python|Diya   |91   |91            |86        |
|Python|Rohan  |86   |91            |84        |
|Python|Meera  |84   |86            |78        |
|Python|Kiran  |78   |84            |NULL      |
|Scala |Aarav  |95   |NULL          |90        |
|Scala |Diya   |90   |95            |90        |
|Scala |Rohan  |90   |90            |85        |
|Scala |Meera  |85   |90            |80        |
|Scala |Kiran  |80   |85            |NULL      |
|Spark |Aarav  |98   |NULL          |92        |
|Spark |Diya   |92   |98            |92        |
|Spark |Rohan  |92   |92            |88        |
|Spark |Meera  |88   |92            |82        |
|Spark |Kiran  |82   |88            |NULL      |
+------+-------+-----+--------------+----------+


===== POLICY HISTORY =====
+-----------+---------+-----------+-------+----------+
|customer_id|policy_id|policy_type|premium|updated_at|
+-----------+---------+-----------+-------+----------+
|C101       |P001     |Health     |12000  |2025-01-10|
|C101       |P002     |Life       |18000  |2025-06-15|
|C101       |P003     |Health     |15000  |2026-02-20|
|C102       |P004     |Vehicle    |9000   |2025-03-12|
|C102       |P005     |Vehicle    |11000  |2026-01-05|
|C103       |P006     |Life       |22000  |2025-08-01|
|C103       |P007     |Life       |25000  |2026-04-18|
|C104       |P008     |Health     |14000  |2026-03-22|
+-----------+---------+-----------+-------+----------+


===== LATEST POLICY PER CUSTOMER =====
+-----------+---------+-----------+-------+----------+
|customer_id|policy_id|policy_type|premium|updated_at|
+-----------+---------+-----------+-------+----------+
|C101       |P003     |Health     |15000  |2026-02-20|
|C102       |P005     |Vehicle    |11000  |2026-01-05|
|C103       |P007     |Life       |25000  |2026-04-18|
|C104       |P008     |Health     |14000  |2026-03-22|
+-----------+---------+-----------+-------+----------+


==================================================
       DAY 17 execution completed successfully.
==================================================
[success] elapsed time: 9 s
```

## Concepts Explained

### 1. `row_number()`

Assigns a unique sequential number to rows inside each window partition. Here, the partition is each course, ordered by score descending. It is useful when you need a fixed number of rows, such as exactly the top three students per course.

### 2. `rank()`

Rows with tied scores receive the same rank. The next rank has a gap. For Python, Aarav and Diya both score 91 and receive rank 1; Rohan's score of 86 receives rank 3.

### 3. `dense_rank()`

Rows with tied scores receive the same rank, but there are no gaps. In Python, Aarav and Diya receive dense rank 1, and Rohan receives dense rank 2.

### 4. `lag()` and `lead()`

- `lag("score", 1)` returns the score from the preceding row in the defined window order.
- `lead("score", 1)` returns the score from the following row.
- `NULL` appears when no preceding or following row exists in that course.

The ordering used for these functions is score descending, then student name ascending.

### 5. Latest Policy per Customer

The policy window partitions records by `customer_id` and orders each customer's records by `updated_at` descending. `row_number() == 1` retains the most recent policy record for each customer. `policy_id` descending is used as a secondary sort for deterministic tie handling.

## Key Results

| Customer | Latest policy | Updated date | Premium |
|---|---|---|---:|
| C101 | P003 | 2026-02-20 | 15,000 |
| C102 | P005 | 2026-01-05 | 11,000 |
| C103 | P007 | 2026-04-18 | 25,000 |
| C104 | P008 | 2026-03-22 | 14,000 |

## How to Run

From the project directory:

```bash
cd ~/scala-spark-30-days-practice/day17_WindowFunctions
sbt run
```

## Completion Status

**Day 17 — Window Functions completed successfully.**

The program ran successfully and demonstrated ranking, top-N selection, neighboring-row comparisons, and latest-record selection using Spark SQL window functions.
