
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object Day17WindowFunctions {

  def main(args: Array[String]): Unit = {

    println()
    println("==================================================")
    println("       DAY 17 - WINDOW FUNCTIONS")
    println("==================================================")

    val spark = SparkSession.builder()
      .appName("Day17-WindowFunctions")
      .master("local[*]")
      .getOrCreate()

    try {
      spark.sparkContext.setLogLevel("ERROR")

      import spark.implicits._

      // =====================================================
      // 1. READ STUDENT SCORES
      // =====================================================

      val students = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/student_scores.csv")

      println("\n===== STUDENT SCORES =====")
      students.show(false)

      // =====================================================
      // 2. ROW_NUMBER, RANK, AND DENSE_RANK
      // =====================================================

      val scoreWindow = Window
        .partitionBy("course")
        .orderBy(desc("score"))

      val rankedStudents = students
        .withColumn("row_number", row_number().over(scoreWindow))
        .withColumn("rank", rank().over(scoreWindow))
        .withColumn("dense_rank", dense_rank().over(scoreWindow))

      println("\n===== ROW_NUMBER, RANK, DENSE_RANK =====")

      rankedStudents
        .orderBy(asc("course"), desc("score"), asc("student"))
        .show(false)

      // =====================================================
      // 3. TOP 3 STUDENTS PER COURSE
      // =====================================================

      println("\n===== TOP 3 STUDENTS PER COURSE =====")

      rankedStudents
        .filter($"row_number" <= 3)
        .orderBy(asc("course"), desc("score"), asc("student"))
        .show(false)

      // =====================================================
      // 4. LAG AND LEAD
      // =====================================================

      val scoreOrderWindow = Window
        .partitionBy("course")
        .orderBy(desc("score"), asc("student"))

      val scoreComparisons = students
        .withColumn(
          "previous_score",
          lag("score", 1).over(scoreOrderWindow)
        )
        .withColumn(
          "next_score",
          lead("score", 1).over(scoreOrderWindow)
        )

      println("\n===== LAG AND LEAD: STUDENT SCORES =====")

      scoreComparisons
        .orderBy(asc("course"), desc("score"), asc("student"))
        .show(false)

      // =====================================================
      // 5. READ POLICY HISTORY
      // =====================================================

      val policies = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/policy_history.csv")

      println("\n===== POLICY HISTORY =====")
      policies.show(false)

      // =====================================================
      // 6. LATEST POLICY PER CUSTOMER
      // =====================================================

      val policyWindow = Window
        .partitionBy("customer_id")
        .orderBy(desc("updated_at"), desc("policy_id"))

      val latestPolicies = policies
        .withColumn("row_number", row_number().over(policyWindow))
        .filter($"row_number" === 1)
        .drop("row_number")

      println("\n===== LATEST POLICY PER CUSTOMER =====")

      latestPolicies
        .orderBy(asc("customer_id"))
        .show(false)

      // =====================================================
      // SUCCESS MESSAGE
      // =====================================================

      println()
      println("==================================================")
      println("       DAY 17 execution completed successfully.")
      println("==================================================")

    } finally {
      spark.stop()
    }
  }
}
