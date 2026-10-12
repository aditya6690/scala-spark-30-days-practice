
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Day16Aggregations {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Day16-Aggregations")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    try {
      println("\n==============================================")
      println("DAY 16 - AGGREGATIONS")
      println("==============================================")

      // 1. Read hospital revenue data
      val hospitalDF = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/hospital_revenue.csv")

      println("\n--- Hospital Revenue Data ---")
      hospitalDF.show(false)

      println("\n--- Hospital Data Schema ---")
      hospitalDF.printSchema()

      // 2. Basic aggregations: count, sum, avg, min, max
      println("\n--- Overall Revenue Statistics ---")

      hospitalDF.agg(
        count("*").as("doctor_records"),
        sum("revenue").as("total_revenue"),
        round(avg("revenue"), 2).as("average_revenue"),
        min("revenue").as("minimum_revenue"),
        max("revenue").as("maximum_revenue"),
        sum("patients").as("total_patients")
      ).show(false)

      // 3. Department-wise salary/revenue statistics
      println("\n--- Department-wise Revenue Statistics ---")

      val departmentStats = hospitalDF
        .groupBy("department")
        .agg(
          count("*").as("doctor_count"),
          sum("revenue").as("total_revenue"),
          round(avg("revenue"), 2).as("average_revenue"),
          min("revenue").as("minimum_revenue"),
          max("revenue").as("maximum_revenue"),
          sum("patients").as("total_patients")
        )
        .orderBy(desc("total_revenue"))

      departmentStats.show(false)

      // 4. Group by multiple columns
      println("\n--- Group by Department and Doctor ---")

      hospitalDF
        .groupBy("department", "doctor")
        .agg(
          sum("revenue").as("doctor_revenue"),
          sum("patients").as("patients")
        )
        .orderBy("department", "doctor")
        .show(false)

      // 5. HAVING-like filtering after aggregation
      // Show departments with total revenue >= 250000.
      println("\n--- Departments with Revenue >= 250000 ---")

      departmentStats
        .filter(col("total_revenue") >= 250000)
        .show(false)

      // 6. Department revenue contribution
      println("\n--- Department Revenue Contribution ---")

      val totalRevenue = hospitalDF
        .agg(sum("revenue").as("grand_total"))
        .first()
        .getAs[Long]("grand_total")

      departmentStats
        .withColumn(
          "revenue_percentage",
          round(col("total_revenue") * 100.0 / lit(totalRevenue), 2)
        )
        .select(
          col("department"),
          col("total_revenue"),
          col("revenue_percentage")
        )
        .show(false)

      // 7. Summary
      println("\n==============================================")
      println("AGGREGATION PRACTICE SUMMARY")
      println("==============================================")
      println("count, sum, avg, min, max Used : Yes")
      println("groupBy Used                   : Yes")
      println("Multiple-column groupBy Used   : Yes")
      println("HAVING-like filter Used        : Yes")
      println("Department revenue metrics     : Yes")
      println("\n==============================================")
      println("Day 16 execution completed successfully.")
      println("==============================================")

    } finally {
      spark.stop()
    }
  }
}
