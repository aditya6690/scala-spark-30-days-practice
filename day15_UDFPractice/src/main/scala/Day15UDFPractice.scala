import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Day15UDFPractice {

  def main(args: Array[String]): Unit = {

    // ============================================================
    // SPARK SESSION
    // ============================================================

    val spark = SparkSession.builder()
      .appName("Day15 UDF Practice")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    println("\n==============================================")
    println("DAY 15 - UDF PRACTICE")
    println("==============================================")

    // ============================================================
    // 1. READ CUSTOMER DATA
    // ============================================================

    val customersDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/customers.csv")

    println("\n--- Customer DataFrame ---")
    customersDF.show(false)

    println("\n--- Customer Schema ---")
    customersDF.printSchema()

    // ============================================================
    // 2. SCALA UDF - SALARY/SPEND BAND CLASSIFICATION
    // ============================================================

    val classifySalaryBand = udf((spend: Double) => {

      if (spend >= 100000) {
        "Platinum"
      } else if (spend >= 75000) {
        "Gold"
      } else if (spend >= 50000) {
        "Silver"
      } else {
        "Bronze"
      }

    })

    // ============================================================
    // 3. ADD CALCULATED COLUMN USING withColumn
    // ============================================================

    val customersWithSalaryBand = customersDF
      .withColumn(
        "salary_band",
        classifySalaryBand(col("total_spend").cast("double"))
      )

    println("\n--- Salary Band Using Scala UDF ---")

    customersWithSalaryBand
      .select(
        col("customer_id"),
        col("name"),
        col("total_spend"),
        col("salary_band")
      )
      .show(false)

    // ============================================================
    // 4. BUILT-IN SPARK FUNCTION COMPARISON
    // ============================================================

    val customersWithBuiltInBand = customersDF
      .withColumn(
        "salary_band_builtin",
        when(col("total_spend") >= 100000, "Platinum")
          .when(col("total_spend") >= 75000, "Gold")
          .when(col("total_spend") >= 50000, "Silver")
          .otherwise("Bronze")
      )

    println("\n--- Salary Band Using Built-in Spark Functions ---")

    customersWithBuiltInBand
      .select(
        col("customer_id"),
        col("name"),
        col("total_spend"),
        col("salary_band_builtin")
      )
      .show(false)

    // ============================================================
    // 5. COMPARE UDF AND BUILT-IN FUNCTION
    // ============================================================

    val comparisonDF = customersDF
      .withColumn(
        "udf_band",
        classifySalaryBand(col("total_spend").cast("double"))
      )
      .withColumn(
        "builtin_band",
        when(col("total_spend") >= 100000, "Platinum")
          .when(col("total_spend") >= 75000, "Gold")
          .when(col("total_spend") >= 50000, "Silver")
          .otherwise("Bronze")
      )
      .withColumn(
        "match",
        col("udf_band") === col("builtin_band")
      )

    println("\n--- UDF vs Built-in Function Comparison ---")

    comparisonDF
      .select(
        col("customer_id"),
        col("name"),
        col("total_spend"),
        col("udf_band"),
        col("builtin_band"),
        col("match")
      )
      .show(false)

    // ============================================================
    // 6. REGISTER UDF WITH SPARK SESSION / CATALOG
    // ============================================================

    spark.udf.register(
      "classify_salary_band",
      (spend: Double) => {

        if (spend >= 100000) {
          "Platinum"
        } else if (spend >= 75000) {
          "Gold"
        } else if (spend >= 50000) {
          "Silver"
        } else {
          "Bronze"
        }

      }
    )

    // ============================================================
    // 7. CREATE TEMPORARY VIEW
    // ============================================================

    customersDF.createOrReplaceTempView("customers")

    // ============================================================
    // 8. USE REGISTERED UDF THROUGH SPARK SQL
    // ============================================================

    val salaryBandSQL = spark.sql(
      """
        SELECT
          customer_id,
          name,
          total_spend,
          classify_salary_band(CAST(total_spend AS DOUBLE)) AS salary_band
        FROM customers
        ORDER BY total_spend DESC
      """
    )

    println("\n--- Registered UDF Using Spark SQL ---")

    salaryBandSQL.show(false)

    // ============================================================
    // 9. CUSTOMER RISK CATEGORY
    // ============================================================
    //
    // Scenario:
    // Create a customer risk category from transaction values.
    //
    // High Risk:
    //   total_spend >= 100000 AND transactions >= 20
    //
    // Medium Risk:
    //   total_spend >= 50000 OR transactions >= 10
    //
    // Low Risk:
    //   everything else
    //
    // ============================================================

    val customerRiskUDF = udf(
      (transactions: Int, spend: Double) => {

        if (spend >= 100000 && transactions >= 20) {
          "High Risk"
        } else if (spend >= 50000 || transactions >= 10) {
          "Medium Risk"
        } else {
          "Low Risk"
        }

      }
    )

    val customersWithRisk = customersDF
      .withColumn(
        "risk_category",
        customerRiskUDF(
          col("total_transactions").cast("int"),
          col("total_spend").cast("double")
        )
      )

    println("\n--- Customer Risk Category ---")

    customersWithRisk
      .select(
        col("customer_id"),
        col("name"),
        col("total_transactions"),
        col("total_spend"),
        col("risk_category")
      )
      .orderBy(desc("total_spend"))
      .show(false)

    // ============================================================
    // 10. RISK CATEGORY SUMMARY
    // ============================================================

    val riskSummary = customersWithRisk
      .groupBy("risk_category")
      .count()
      .orderBy("risk_category")

    println("\n--- Risk Category Summary ---")

    riskSummary.show(false)

    // ============================================================
    // 11. FINAL UDF SUMMARY
    // ============================================================

    println("\n==============================================")
    println("UDF PRACTICE SUMMARY")
    println("==============================================")

    println("Scala UDF Created          : Yes")
    println("withColumn Used            : Yes")
    println("Built-in Function Used     : Yes")
    println("UDF vs Built-in Compared   : Yes")
    println("UDF Registered in Catalog  : Yes")
    println("Spark SQL UDF Used         : Yes")
    println("Customer Risk UDF          : Yes")

    println("\n==============================================")
    println("Day 15 execution completed successfully.")
    println("==============================================")

    spark.stop()
  }
}
