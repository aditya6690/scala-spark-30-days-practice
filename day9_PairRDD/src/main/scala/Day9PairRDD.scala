import org.apache.spark.sql.SparkSession

object Day9PairRDD {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("        DAY 9 - PAIR RDD")
    println("========================================")

    val spark = SparkSession.builder()
      .appName("Day9PairRDD")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")

    // --------------------------------------------
    // 1. READ SALES DATA
    // --------------------------------------------

    println("\n========== 1. READ SALES DATA ==========")

    val lines = sc.textFile("data/sales.txt")

    println("Sales Data:")
    lines.collect().foreach(println)

    // --------------------------------------------
    // 2. CREATE PAIR RDD
    // --------------------------------------------

    println("\n========== 2. CREATE PAIR RDD ==========")

    val salesRDD = lines.map { line =>
      val parts = line.split(" ")
      (parts(0), parts(1).toDouble)
    }

    println("Pair RDD:")
    salesRDD.collect().foreach(println)

    // --------------------------------------------
    // 3. REDUCE BY KEY
    // --------------------------------------------

    println("\n========== 3. REDUCE BY KEY ==========")

    val totalSales = salesRDD.reduceByKey(_ + _)

    println("Total Sales by Product:")
    totalSales.collect().sortBy(_._1).foreach(println)

    // --------------------------------------------
    // 4. GROUP BY KEY
    // --------------------------------------------

    println("\n========== 4. GROUP BY KEY ==========")

    val groupedSales = salesRDD.groupByKey()

    println("Sales Values by Product:")
    groupedSales.collect().sortBy(_._1).foreach {
      case (product, values) =>
        println(s"$product -> ${values.toList}")
    }

    // --------------------------------------------
    // 5. SORT BY KEY
    // --------------------------------------------

    println("\n========== 5. SORT BY KEY ==========")

    val sortedSales = totalSales.sortByKey()

    println("Products Sorted by Name:")
    sortedSales.collect().foreach(println)

    // --------------------------------------------
    // 6. SORT BY VALUE
    // --------------------------------------------

    println("\n========== 6. SORT BY VALUE ==========")

    val sortedBySales = totalSales
      .sortBy { case (_, amount) => amount }
      .map { case (product, amount) => (product, amount) }

    println("Products Sorted by Total Sales:")
    sortedBySales.collect().foreach(println)

    // --------------------------------------------
    // 7. HIGHEST SALES
    // --------------------------------------------

    println("\n========== 7. HIGHEST SALES ==========")

    val highestSale = totalSales
      .sortBy { case (_, amount) => amount }
      .takeOrdered(1)(Ordering[Double].reverse.on[(String, Double)](_._2))

    println("Highest Selling Product:")
    highestSale.foreach(println)

    // --------------------------------------------
    // 8. PARTITIONS
    // --------------------------------------------

    println("\n========== 8. PARTITIONS ==========")

    println(s"Input RDD Partitions : ${lines.getNumPartitions}")
    println(s"Pair RDD Partitions  : ${salesRDD.getNumPartitions}")
    println(s"Default Parallelism  : ${sc.defaultParallelism}")

    spark.stop()

    println("\n========================================")
    println("Day 9 completed successfully!")
    println("Aditya completed Pair RDD practice.")
    println("========================================")
  }
}
