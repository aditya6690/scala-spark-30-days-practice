# Day 2 – Scala Collections Practice

## Objective

The objective of Day 2 is to strengthen Scala collection skills by processing sales, customer, order, product, quantity, and price data using Scala collections without Apache Spark.

## Topics Covered

- Process a sales `List` using `map`, `filter`, `flatMap`, and `reduce`
- Use `Vector` for indexed customer records
- Use `Map` for product quantities and prices
- Use a for-comprehension to combine customers and orders
- Produce a daily sales summary without Spark

## Scenario

Build a small daily sales processor using only Scala collections.

## Project Structure

```text
day2_ScalaCollectionsPractice/
├── build.sbt
├── Output
├── .gitignore
├── README.md
└── src/
    └── main/
        └── scala/
            └── Day2ScalaCollectionsPractice.scala
```

## Technologies Used

- Scala 2.12.18
- SBT
- Java 17


## How to Run

```bash
cd ~/scala-spark-practice/day2_ScalaCollectionsPractice
sbt compile
sbt run
```

## Sample Output

```text
========================================
      DAY 2 - SCALA COLLECTIONS
========================================

--- Sales List ---
Original Sales: List(1200.0, 850.0, 450.0, 2200.0, 1500.0)
Sales after 10% discount: List(1080.0, 765.0, 405.0, 1980.0, 1350.0)
Sales greater than 1000: List(1200.0, 2200.0, 1500.0)
Total Sales: 6200.0

--- flatMap Example ---
Flattened Sales: List(1000.0, 500.0, 750.0, 250.0, 1200.0)
Flattened Total: 3700.0

--- Vector - Indexed Customer Records ---
Customer at index 0: (101,Aditya)
Customer at index 1: (102,Rahul)
Customer at index 2: (103,Priya)
Total Customers: 4

--- Map - Product Quantities and Prices ---
Laptop -> Quantity: 2, Price: 50000.0, Sales: 100000.0
Mouse -> Quantity: 5, Price: 800.0, Sales: 4000.0
Keyboard -> Quantity: 3, Price: 1500.0, Sales: 4500.0
Monitor -> Quantity: 2, Price: 12000.0, Sales: 24000.0
Product Sales Total: 132500.0

--- For-Comprehension: Customers and Orders ---
Aditya ordered Laptop, Quantity: 1, Amount: 50000.0
Aditya ordered Mouse, Quantity: 2, Amount: 1600.0
Rahul ordered Keyboard, Quantity: 1, Amount: 1500.0
Priya ordered Monitor, Quantity: 1, Amount: 12000.0
Sneha ordered Mouse, Quantity: 3, Amount: 2400.0

--- Daily Sales Summary ---
Number of Orders: 5
Total Daily Sales: 67500.0
Average Order Value: 13500.00
Highest Order Value: 50000.0
Lowest Order Value: 1500.0

--- Product-wise Sales ---
Keyboard -> 1500.0
Laptop -> 50000.0
Monitor -> 12000.0
Mouse -> 4000.0

========================================
Day 2 completed successfully!
Aditya completed the Scala Collections Practice.
========================================
```
