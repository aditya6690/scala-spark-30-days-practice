import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Day13SparkSQLBasics {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Day13-Spark-SQL-Basics")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    println("==============================================")
    println("          DAY 13 - SPARK SQL BASICS")
    println("==============================================")

    // --------------------------------------------------
    // 1. Create DataFrame from CSV
    // --------------------------------------------------
    val customersDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/customers.csv")

    println("\nCustomer DataFrame:")
    customersDF.show()

    // --------------------------------------------------
    // 2. Inspect schema
    // --------------------------------------------------
    println("\nDataFrame Schema:")
    customersDF.printSchema()

    // --------------------------------------------------
    // 3. Select columns
    // --------------------------------------------------
    println("\nSelected Columns:")
    customersDF
      .select("customer_id", "name", "city", "total_spend")
      .show()

    // --------------------------------------------------
    // 4. Filter customers
    // --------------------------------------------------
    println("\nCustomers with Spend Greater Than 50000:")
    customersDF
      .filter(col("total_spend") > 50000)
      .select("customer_id", "name", "city", "total_spend")
      .show()

    // --------------------------------------------------
    // 5. Add calculated column using withColumn
    // --------------------------------------------------
    val enrichedCustomersDF = customersDF.withColumn(
      "customer_segment",
      when(col("total_spend") >= 80000, "Premium")
        .when(col("total_spend") >= 50000, "Regular")
        .otherwise("Basic")
    )

    println("\nCustomer Segmentation:")
    enrichedCustomersDF
      .select(
        "customer_id",
        "name",
        "city",
        "total_spend",
        "customer_segment"
      )
      .show()

    // --------------------------------------------------
    // 6. Create temporary view
    // --------------------------------------------------
    enrichedCustomersDF.createOrReplaceTempView("customers")

    println("\nTemporary View 'customers' created successfully.")

    // --------------------------------------------------
    // 7. Run Spark SQL query
    // --------------------------------------------------
    println("\nSQL Report - Premium Customers:")

    val premiumCustomers = spark.sql(
      """
        SELECT
          customer_id,
          name,
          city,
          total_spend,
          customer_segment
        FROM customers
        WHERE customer_segment = 'Premium'
        ORDER BY total_spend DESC
      """
    )

    premiumCustomers.show()

    // --------------------------------------------------
    // 8. City-wise customer analytics
    // --------------------------------------------------
    println("\nSQL Report - City-wise Customer Analytics:")

    val cityReport = spark.sql(
      """
        SELECT
          city,
          COUNT(*) AS customer_count,
          ROUND(AVG(total_spend), 2) AS average_spend,
          SUM(total_spend) AS total_city_spend
        FROM customers
        GROUP BY city
        ORDER BY total_city_spend DESC
      """
    )

    cityReport.show()

    // --------------------------------------------------
    // 9. Overall customer analytics
    // --------------------------------------------------
    println("\nSQL Report - Overall Customer Analytics:")

    val overallReport = spark.sql(
      """
        SELECT
          COUNT(*) AS total_customers,
          ROUND(AVG(total_spend), 2) AS average_spend,
          SUM(total_spend) AS total_spend,
          MAX(total_spend) AS maximum_spend,
          MIN(total_spend) AS minimum_spend
        FROM customers
      """
    )

    overallReport.show()

    // --------------------------------------------------
    // 10. Explain DataFrame operations
    // --------------------------------------------------
    println("\nDay 13 Concepts Demonstrated:")
    println("----------------------------------------------")
    println("1. DataFrame created from CSV")
    println("2. Schema inspection using printSchema()")
    println("3. Column selection using select()")
    println("4. Row filtering using filter()")
    println("5. Calculated column using withColumn()")
    println("6. Temporary view using createOrReplaceTempView()")
    println("7. SQL queries using spark.sql()")
    println("8. Customer analytics using GROUP BY and aggregate functions")

    println("\n==============================================")
    println("Day 13 execution completed successfully.")
    println("==============================================")

    spark.stop()
  }
}
