object Day1ScalaEssentials {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("       DAY 1 - SCALA ESSENTIALS")
    println("========================================")

    // --------------------------------------------------
    // 1. val, var and lazy val
    // --------------------------------------------------

    val studentName = "Aditya"
    var completedTopics = 0

    lazy val welcomeMessage = {
      println("Lazy value is being evaluated...")
      "Welcome to Scala Essentials!"
    }

    println("\n--- val, var and lazy val ---")

    println(s"Student Name: $studentName")

    completedTopics += 1

    println(s"Completed Topics: $completedTopics")

    println("Accessing lazy value:")
    println(welcomeMessage)


    // --------------------------------------------------
    // 2. Immutable Collections
    // --------------------------------------------------

    println("\n--- Immutable Collections ---")

    val marks = List(85, 72, 90, 65, 78)

    println(s"Original Marks: $marks")

    val increasedMarks = marks.map(mark => mark + 5)

    println(s"Marks after adding 5: $increasedMarks")

    val passedMarks = marks.filter(mark => mark >= 70)

    println(s"Passed Marks: $passedMarks")


    // --------------------------------------------------
    // 3. For-comprehension with yield
    // --------------------------------------------------

    println("\n--- For-Comprehension with yield ---")

    val students = List(
      ("Aditya", 85),
      ("Rahul", 72),
      ("Priya", 90),
      ("Sneha", 65)
    )

    val studentResults = for {
      (name, mark) <- students
    } yield {

      val grade =
        if (mark >= 90) "A+"
        else if (mark >= 80) "A"
        else if (mark >= 70) "B"
        else if (mark >= 60) "C"
        else "F"

      s"$name scored $mark and received grade $grade"
    }

    studentResults.foreach(println)


    // --------------------------------------------------
    // 4. List, Vector, Set and Map
    // --------------------------------------------------

    println("\n--- List, Vector, Set and Map ---")

    val studentList =
      List("Aditya", "Rahul", "Priya", "Sneha")

    println(s"List: $studentList")

    val studentVector =
      Vector("Aditya", "Rahul", "Priya", "Sneha")

    println(s"Vector: $studentVector")

    println(
      s"Vector element at index 2: ${studentVector(2)}"
    )

    val subjects =
      Set("Scala", "Spark", "Scala", "SQL")

    println(s"Set: $subjects")

    val studentMarks = Map(
      "Aditya" -> 85,
      "Rahul" -> 72,
      "Priya" -> 90,
      "Sneha" -> 65
    )

    println(s"Map: $studentMarks")

    println(
      s"Aditya's Mark: ${studentMarks("Aditya")}"
    )


    // --------------------------------------------------
    // 5. Logger Trait
    // --------------------------------------------------

    println("\n--- Trait Example ---")

    trait Logger {

      def log(message: String): Unit
    }

    class StudentLogger extends Logger {

      override def log(message: String): Unit = {
        println(s"[STUDENT LOG] $message")
      }
    }

    class GradeLogger extends Logger {

      override def log(message: String): Unit = {
        println(s"[GRADE LOG] $message")
      }
    }

    val studentLogger = new StudentLogger
    val gradeLogger = new GradeLogger

    studentLogger.log(
      "Student data processed successfully."
    )

    gradeLogger.log(
      "Grade calculation completed."
    )


    // --------------------------------------------------
    // 6. Small Student Grade Processor
    // --------------------------------------------------

    println("\n--- Student Grade Processor ---")

    val studentData = List(
      ("Aditya", 85),
      ("Rahul", 72),
      ("Priya", 95),
      ("Sneha", 58),
      ("Amit", 68)
    )

    val gradeResults = studentData.map {

      case (name, mark) if mark >= 90 =>
        (name, mark, "A+")

      case (name, mark) if mark >= 80 =>
        (name, mark, "A")

      case (name, mark) if mark >= 70 =>
        (name, mark, "B")

      case (name, mark) if mark >= 60 =>
        (name, mark, "C")

      case (name, mark) =>
        (name, mark, "F")
    }

    gradeResults.foreach {

      case (name, mark, grade) =>
        println(
          s"$name -> Marks: $mark, Grade: $grade"
        )
    }


    // --------------------------------------------------
    // 7. Passed and Failed Students
    // --------------------------------------------------

    println("\n--- Passed and Failed Students ---")

    val passedStudents = gradeResults.filter {

      case (_, mark, _) =>
        mark >= 60
    }

    val failedStudents = gradeResults.filter {

      case (_, mark, _) =>
        mark < 60
    }

    println("Passed Students:")

    passedStudents.foreach {

      case (name, mark, grade) =>
        println(
          s"$name -> $mark -> $grade"
        )
    }

    println("Failed Students:")

    failedStudents.foreach {

      case (name, mark, grade) =>
        println(
          s"$name -> $mark -> $grade"
        )
    }


    // --------------------------------------------------
    // 8. Marks Statistics
    // --------------------------------------------------

    println("\n--- Marks Statistics ---")

    val allMarks = studentData.map {

      case (_, mark) =>
        mark
    }

    if (allMarks.nonEmpty) {

      val totalMarks = allMarks.sum

      val highestMark = allMarks.max

      val lowestMark = allMarks.min

      val averageMark =
        allMarks.sum.toDouble / allMarks.size

      println(s"Total Marks: $totalMarks")

      println(s"Highest Mark: $highestMark")

      println(s"Lowest Mark: $lowestMark")

      println(
        f"Average Mark: $averageMark%.2f"
      )

    } else {

      println("No marks available.")
    }


    // --------------------------------------------------
    // 9. Immutability Demonstration
    // --------------------------------------------------

    println("\n--- Immutability Demonstration ---")

    val originalMarks =
      List(70, 80, 90)

    val updatedMarks =
      originalMarks.map(mark => mark + 10)

    println(
      s"Original List: $originalMarks"
    )

    println(
      s"New List: $updatedMarks"
    )

    println(
      "The original immutable List was not changed."
    )


    // --------------------------------------------------
    // 10. Completion
    // --------------------------------------------------

    println("\n========================================")

    println("Day 1 completed successfully!")

    println(
      "Aditya completed the Scala Essentials practice."
    )

    println("========================================")
  }
}
