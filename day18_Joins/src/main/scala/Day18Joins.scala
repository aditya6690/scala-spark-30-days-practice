
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Day18Joins {

  def main(args: Array[String]): Unit = {

    println()
    println("==================================================")
    println("             DAY 18 - SPARK JOINS")
    println("==================================================")

    val spark = SparkSession.builder()
      .appName("Day18-Joins")
      .master("local[*]")
      .getOrCreate()

    try {
      spark.sparkContext.setLogLevel("ERROR")

      // Disable automatic broadcast joins so that the
      // physical plan can demonstrate sort merge joins.
      spark.conf.set("spark.sql.autoBroadcastJoinThreshold", "-1")
      spark.conf.set("spark.sql.adaptive.enabled", "false")

      // ==================================================
      // 1. READ DATASETS
      // ==================================================

      val customers = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/customers.csv")

      val orders = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/orders.csv")

      val payments = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/payments.csv")

      println("\n===== CUSTOMERS =====")
      customers.show(false)

      println("\n===== ORDERS =====")
      orders.show(false)

      println("\n===== PAYMENTS =====")
      payments.show(false)

      // ==================================================
      // 2. INNER JOIN
      // Orders that have a matching customer.
      // ==================================================

      println("\n===== INNER JOIN: ORDERS AND CUSTOMERS =====")

      val innerJoin = orders.alias("o")
        .join(
          customers.alias("c"),
          col("o.customer_id") === col("c.customer_id"),
          "inner"
        )
        .select(
          col("o.order_id").as("order_id"),
          col("o.customer_id").as("customer_id"),
          col("c.name").as("customer_name"),
          col("c.city").as("city"),
          col("o.product").as("product"),
          col("o.amount").as("amount")
        )

      innerJoin.orderBy("order_id").show(false)

      // ==================================================
      // 3. LEFT OUTER JOIN
      // Keep every order, even without a matching customer.
      // ==================================================

      println("\n===== LEFT JOIN: ALL ORDERS =====")

      val leftJoin = orders.alias("o")
        .join(
          customers.alias("c"),
          col("o.customer_id") === col("c.customer_id"),
          "left"
        )
        .select(
          col("o.order_id").as("order_id"),
          col("o.customer_id").as("customer_id"),
          coalesce(col("c.name"), lit("UNKNOWN CUSTOMER"))
            .as("customer_name"),
          col("o.product").as("product"),
          col("o.amount").as("amount")
        )

      leftJoin.orderBy("order_id").show(false)

      // ==================================================
      // 4. RIGHT OUTER JOIN
      // Keep every customer, even without an order.
      // ==================================================

      println("\n===== RIGHT JOIN: ALL CUSTOMERS =====")

      val rightJoin = orders.alias("o")
        .join(
          customers.alias("c"),
          col("o.customer_id") === col("c.customer_id"),
          "right"
        )
        .select(
          col("c.customer_id").as("customer_id"),
          col("c.name").as("customer_name"),
          col("o.order_id").as("order_id"),
          col("o.product").as("product")
        )

      rightJoin
        .orderBy(asc("customer_id"), asc("order_id"))
        .show(false)

      // ==================================================
      // 5. FULL OUTER JOIN
      // Keep matched and unmatched orders and customers.
      // ==================================================

      println("\n===== FULL OUTER JOIN: ORDERS AND CUSTOMERS =====")

      val fullJoin = orders.alias("o")
        .join(
          customers.alias("c"),
          col("o.customer_id") === col("c.customer_id"),
          "full_outer"
        )
        .select(
          coalesce(
            col("o.customer_id"),
            col("c.customer_id")
          ).as("customer_id"),
          col("c.name").as("customer_name"),
          col("o.order_id").as("order_id"),
          col("o.product").as("product")
        )

      fullJoin
        .orderBy(asc("customer_id"), asc("order_id"))
        .show(false)

      // ==================================================
      // 6. JOIN ORDERS, CUSTOMERS, AND PAYMENTS
      // Keep all orders and replace missing values.
      // ==================================================

      println("\n===== COMPLETE ORDER PAYMENT REPORT =====")

      val orderPaymentReport = orders.alias("o")
        .join(
          customers.alias("c"),
          col("o.customer_id") === col("c.customer_id"),
          "left"
        )
        .join(
          payments.alias("p"),
          col("o.order_id") === col("p.order_id"),
          "left"
        )
        .select(
          col("o.order_id").as("order_id"),
          col("o.customer_id").as("customer_id"),
          coalesce(col("c.name"), lit("UNKNOWN CUSTOMER"))
            .as("customer_name"),
          col("o.product").as("product"),
          col("o.amount").as("order_amount"),
          coalesce(col("p.payment_amount"), lit(0))
            .as("payment_amount"),
          coalesce(col("p.payment_status"), lit("NOT PAID"))
            .as("payment_status")
        )

      orderPaymentReport
        .orderBy("order_id")
        .show(false)

      // ==================================================
      // 7. INSPECT PHYSICAL JOIN PLAN
      // ==================================================

      println("\n===== PHYSICAL PLAN: INNER JOIN =====")

      innerJoin.explain(true)

      // ==================================================
      // 8. COMPLETION MESSAGE
      // ==================================================

      println()
      println("==================================================")
      println("       DAY 18 execution completed successfully.")
      println("==================================================")

    } finally {
      spark.stop()
    }
  }
}
