# Day 1 – Scala Essentials

This project contains the Day 1 practice program for learning Scala fundamentals before working with Apache Spark.

## Project Objective

The objective is to build a strong foundation in Scala programming useful for Apache Spark development.

The Day 1 program covers:
- `val`, `var`, and `lazy val`
- Immutable collections
- `List`, `Vector`, `Set`, and `Map`
- `map()` and `filter()`
- For-comprehension with `yield`
- Traits and classes
- Pattern matching
- Student grade processing
- Passed and failed student filtering
- Basic statistics
- Immutability

## Technologies Used

- Scala 2.12.18
- SBT
- JDK
- Linux / Ubuntu

## Project Structure

```text
day1_ScalaEssentials/
├── build.sbt
├── README.md
└── src/
    └── main/
        └── scala/
            └── Day1ScalaEssentials.scala
```

## Project Configuration

```scala
name := "Day1-Scala-Essentials"

version := "0.1"

scalaVersion := "2.12.18"
```

## Concepts Covered

### 1. val, var and lazy val

`val` is immutable, `var` is mutable, and `lazy val` is evaluated only when first accessed.

```scala
val studentName = "Aditya"
var completedTopics = 0
completedTopics += 1

lazy val welcomeMessage = {
  println("Lazy value is being evaluated...")
  "Welcome to Scala Essentials!"
}
```

### 2. Immutable Collections

```scala
val marks = List(85, 72, 90, 65, 78)

val increasedMarks = marks.map(mark => mark + 5)
val passedMarks = marks.filter(mark => mark >= 70)
```

The original list remains unchanged.

### 3. For-Comprehension with yield

The program processes student names and marks using a for-comprehension and generates grade results.

### 4. List, Vector, Set and Map

The project demonstrates the four common Scala collection types.

A `Set` stores unique elements, while a `Map` stores key-value pairs.

### 5. Trait and Classes

A `Logger` trait is implemented by `StudentLogger` and `GradeLogger` to demonstrate abstraction and inheritance.

### 6. Student Grade Processor

Student marks are converted into grades using pattern matching.

| Marks | Grade |
|---:|:---|
| 90 or above | A+ |
| 80–89 | A |
| 70–79 | B |
| 60–69 | C |
| Below 60 | F |

### 7. Passed and Failed Students

Students with marks `>= 60` are considered passed. Students with marks `< 60` are considered failed.

### 8. Marks Statistics

The program calculates:
- Total marks
- Highest mark
- Lowest mark
- Average mark

For the current student data:
- Total Marks: **378**
- Highest Mark: **95**
- Lowest Mark: **58**
- Average Mark: **75.60**

### 9. Immutability Demonstration

```scala
val originalMarks = List(70, 80, 90)
val updatedMarks = originalMarks.map(mark => mark + 10)
```

The original immutable list is not modified.

---

# How to Run

Go to the project directory:

```bash
cd ~/scala-spark-practice/day1_ScalaEssentials
```

Compile:

```bash
sbt clean
sbt compile
```

Run:

```bash
sbt run
```

---

# Complete Expected Output

When you run:

```bash
sbt run
```

the complete program output is:

```text
========================================
       DAY 1 - SCALA ESSENTIALS
========================================

--- val, var and lazy val ---
Student Name: Aditya
Completed Topics: 1
Accessing lazy value:
Lazy value is being evaluated...
Welcome to Scala Essentials!

--- Immutable Collections ---
Original Marks: List(85, 72, 90, 65, 78)
Marks after adding 5: List(90, 77, 95, 70, 83)
Passed Marks: List(85, 72, 90, 78)

--- For-Comprehension with yield ---
Aditya scored 85 and received grade A
Rahul scored 72 and received grade B
Priya scored 90 and received grade A+
Sneha scored 65 and received grade C

--- List, Vector, Set and Map ---
List: List(Aditya, Rahul, Priya, Sneha)
Vector: Vector(Aditya, Rahul, Priya, Sneha)
Vector element at index 2: Priya
Set: Set(Scala, Spark, SQL)
Map: Map(Aditya -> 85, Rahul -> 72, Priya -> 90, Sneha -> 65)
Aditya's Mark: 85

--- Trait Example ---
[STUDENT LOG] Student data processed successfully.
[GRADE LOG] Grade calculation completed.

--- Student Grade Processor ---
Aditya -> Marks: 85, Grade: A
Rahul -> Marks: 72, Grade: B
Priya -> Marks: 95, Grade: A+
Sneha -> Marks: 58, Grade: F
Amit -> Marks: 68, Grade: C

--- Passed and Failed Students ---
Passed Students:
Aditya -> 85 -> A
Rahul -> 72 -> B
Priya -> 95 -> A+
Amit -> 68 -> C
Failed Students:
Sneha -> 58 -> F

--- Marks Statistics ---
Total Marks: 378
Highest Mark: 95
Lowest Mark: 58
Average Mark: 75.60

--- Immutability Demonstration ---
Original List: List(70, 80, 90)
New List: List(80, 90, 100)
The original immutable List was not changed.

========================================
Day 1 completed successfully!
Aditya completed the Scala Essentials practice.
========================================
```

