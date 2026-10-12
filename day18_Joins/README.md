# Day 18 --- Spark Joins

## Objective

Practice Apache Spark DataFrame joins using customer, order, and payment
datasets. This README documents the exact console output supplied for
the successful Day 18 run, followed by a short explanation of the
concepts.

## Topics Covered

-   Inner join between orders and customers
-   Left join preserving all orders
-   Right join preserving all customers
-   Full outer join preserving matched and unmatched records
-   Three-way order and payment report
-   Replacing missing values with readable defaults
-   Inspecting parsed, analyzed, optimized, and physical query plans
-   Identifying `SortMergeJoin` in the physical plan

## Project Structure

``` text
day18_Joins/
├── build.sbt
├── Output
├── README.md
├── data/
│   ├── customers.csv
│   ├── orders.csv
│   └── payments.csv
└── src/
    └── main/
        ├── resources/
        │   └── log4j2.properties
        └── scala/
            └── Day18Joins.scala
```

## Dataset Files

### `data/customers.csv`

``` csv
customer_id,name,city
C101,Aarav,Hyderabad
C102,Diya,Warangal
C103,Rohan,Karimnagar
C104,Meera,Nizamabad
```

### `data/orders.csv`

``` csv
order_id,customer_id,product,amount
O501,C101,Laptop,65000
O502,C102,Phone,25000
O503,C101,Mouse,1200
O504,C104,Monitor,12000
O505,C105,Keyboard,2500
```

### `data/payments.csv`

``` csv
payment_id,order_id,payment_amount,payment_status
P901,O501,65000,PAID
P902,O502,25000,PAID
P903,O503,1200,PENDING
P904,O506,7000,PAID
```

## Complete Console Output

The following is the full console output provided for this run,
preserved as supplied.

``` text
==================================================
             DAY 18 - SPARK JOINS
==================================================

===== CUSTOMERS =====
+-----------+-----+----------+
|customer_id|name |city      |
+-----------+-----+----------+
|C101       |Aarav|Hyderabad |
|C102       |Diya |Warangal  |
|C103       |Rohan|Karimnagar|
|C104       |Meera|Nizamabad |
+-----------+-----+----------+


===== ORDERS =====
+--------+-----------+--------+------+
|order_id|customer_id|product |amount|
+--------+-----------+--------+------+
|O501    |C101       |Laptop  |65000 |
|O502    |C102       |Phone   |25000 |
|O503    |C101       |Mouse   |1200  |
|O504    |C104       |Monitor |12000 |
|O505    |C105       |Keyboard|2500  |
+--------+-----------+--------+------+


===== PAYMENTS =====
+----------+--------+--------------+--------------+
|payment_id|order_id|payment_amount|payment_status|
+----------+--------+--------------+--------------+
|P901      |O501    |65000         |PAID          |
|P902      |O502    |25000         |PAID          |
|P903      |O503    |1200          |PENDING       |
|P904      |O506    |7000          |PAID          |
+----------+--------+--------------+--------------+


===== INNER JOIN: ORDERS AND CUSTOMERS =====
+--------+-----------+-------------+---------+-------+------+
|order_id|customer_id|customer_name|city     |product|amount|
+--------+-----------+-------------+---------+-------+------+
|O501    |C101       |Aarav        |Hyderabad|Laptop |65000 |
|O502    |C102       |Diya         |Warangal |Phone  |25000 |
|O503    |C101       |Aarav        |Hyderabad|Mouse  |1200  |
|O504    |C104       |Meera        |Nizamabad|Monitor|12000 |
+--------+-----------+-------------+---------+-------+------+


===== LEFT JOIN: ALL ORDERS =====
+--------+-----------+----------------+--------+------+
|order_id|customer_id|customer_name   |product |amount|
+--------+-----------+----------------+--------+------+
|O501    |C101       |Aarav           |Laptop  |65000 |
|O502    |C102       |Diya            |Phone   |25000 |
|O503    |C101       |Aarav           |Mouse   |1200  |
|O504    |C104       |Meera           |Monitor |12000 |
|O505    |C105       |UNKNOWN CUSTOMER|Keyboard|2500  |
+--------+-----------+----------------+--------+------+


===== RIGHT JOIN: ALL CUSTOMERS =====
+-----------+-------------+--------+-------+
|customer_id|customer_name|order_id|product|
+-----------+-------------+--------+-------+
|C101       |Aarav        |O501    |Laptop |
|C101       |Aarav        |O503    |Mouse  |
|C102       |Diya         |O502    |Phone  |
|C103       |Rohan        |NULL    |NULL   |
|C104       |Meera        |O504    |Monitor|
+-----------+-------------+--------+-------+


===== FULL OUTER JOIN: ORDERS AND CUSTOMERS =====
+-----------+-------------+--------+--------+
|customer_id|customer_name|order_id|product |
+-----------+-------------+--------+--------+
|C101       |Aarav        |O501    |Laptop  |
|C101       |Aarav        |O503    |Mouse   |
|C102       |Diya         |O502    |Phone   |
|C103       |Rohan        |NULL    |NULL    |
|C104       |Meera        |O504    |Monitor |
|C105       |NULL         |O505    |Keyboard|
+-----------+-------------+--------+--------+


===== COMPLETE ORDER PAYMENT REPORT =====
+--------+-----------+----------------+--------+------------+--------------+--------------+
|order_id|customer_id|customer_name   |product |order_amount|payment_amount|payment_status|
+--------+-----------+----------------+--------+------------+--------------+--------------+
|O501    |C101       |Aarav           |Laptop  |65000       |65000         |PAID          |
|O502    |C102       |Diya            |Phone   |25000       |25000         |PAID          |
|O503    |C101       |Aarav           |Mouse   |1200        |1200          |PENDING       |
|O504    |C104       |Meera           |Monitor |12000       |0             |NOT PAID      |
|O505    |C105       |UNKNOWN CUSTOMER|Keyboard|2500        |0             |NOT PAID      |
+--------+-----------+----------------+--------+------------+--------------+--------------+


===== PHYSICAL PLAN: INNER JOIN =====
== Parsed Logical Plan ==
'Project ['o.order_id AS order_id#148, 'o.customer_id AS customer_id#149, 'c.name AS customer_name#150, 'c.city AS city#151, 'o.product AS product#152, 'o.amount AS amount#153]
+- Join Inner, (customer_id#41 = customer_id#17)
   :- SubqueryAlias o
   :  +- Relation [order_id#40,customer_id#41,product#42,amount#43] csv
   +- SubqueryAlias c
      +- Relation [customer_id#17,name#18,city#19] csv

== Analyzed Logical Plan ==
order_id: string, customer_id: string, customer_name: string, city: string, product: string, amount: int
Project [order_id#40 AS order_id#148, customer_id#41 AS customer_id#149, name#18 AS customer_name#150, city#19 AS city#151, product#42 AS product#152, amount#43 AS amount#153]
+- Join Inner, (customer_id#41 = customer_id#17)
   :- SubqueryAlias o
   :  +- Relation [order_id#40,customer_id#41,product#42,amount#43] csv
   +- SubqueryAlias c
      +- Relation [customer_id#17,name#18,city#19] csv

== Optimized Logical Plan ==
Project [order_id#40, customer_id#41, name#18 AS customer_name#150, city#19, product#42, amount#43]
+- Join Inner, (customer_id#41 = customer_id#17)
   :- Filter isnotnull(customer_id#41)
   :  +- Relation [order_id#40,customer_id#41,product#42,amount#43] csv
   +- Filter isnotnull(customer_id#17)
      +- Relation [customer_id#17,name#18,city#19] csv

== Physical Plan ==
*(5) Project [order_id#40, customer_id#41, name#18 AS customer_name#150, city#19, product#42, amount#43]
+- *(5) SortMergeJoin [customer_id#41], [customer_id#17], Inner
   :- *(2) Sort [customer_id#41 ASC NULLS FIRST], false, 0
   :  +- Exchange hashpartitioning(customer_id#41, 200), ENSURE_REQUIREMENTS, [plan_id=486]
   :     +- *(1) Filter isnotnull(customer_id#41)
   :        +- FileScan csv [order_id#40,customer_id#41,product#42,amount#43] Batched: false, DataFilters: [isnotnull(customer_id#41)], Format: CSV, Location: InMemoryFileIndex(1 paths)[file:/home/aditya/scala-spark-30-days-practice/day18_Joins/data/orders..., PartitionFilters: [], PushedFilters: [IsNotNull(customer_id)], ReadSchema: struct<order_id:string,customer_id:string,product:string,amount:int>
   +- *(4) Sort [customer_id#17 ASC NULLS FIRST], false, 0
      +- Exchange hashpartitioning(customer_id#17, 200), ENSURE_REQUIREMENTS, [plan_id=494]
         +- *(3) Filter isnotnull(customer_id#17)
            +- FileScan csv [customer_id#17,name#18,city#19] Batched: false, DataFilters: [isnotnull(customer_id#17)], Format: CSV, Location: InMemoryFileIndex(1 paths)[file:/home/aditya/scala-spark-30-days-practice/day18_Joins/data/custom..., PartitionFilters: [], PushedFilters: [IsNotNull(customer_id)], ReadSchema: struct<customer_id:string,name:string,city:string>


==================================================
       DAY 18 execution completed successfully.
==================================================
[success] elapsed time: 22 s
```