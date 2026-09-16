import org.apache.spark.sql.SparkSession

object Day5TransformationsActions {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("   DAY 5 - TRANSFORMATIONS & ACTIONS")
    println("========================================")

    // --------------------------------------------------
    // Create SparkSession
    // --------------------------------------------------

    val spark = SparkSession.builder()
      .appName("Day5TransformationsActions")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")

    // ==================================================
    // 1. Create RDD
    // ==================================================

    println("\n========== 1. CREATE RDD ==========")

    val numbers = sc.parallelize(List(1, 2, 3, 4, 5, 5, 6, 7, 8, 8))

    println("Original RDD:")
    numbers.collect().foreach(println)

    // ==================================================
    // 2. MAP TRANSFORMATION
    // ==================================================

    println("\n========== 2. MAP ==========")

    val squaredNumbers = numbers.map(x => x * x)

    println("Squared Numbers:")
    squaredNumbers.collect().foreach(println)

    // ==================================================
    // 3. FILTER TRANSFORMATION
    // ==================================================

    println("\n========== 3. FILTER ==========")

    val evenNumbers = numbers.filter(x => x % 2 == 0)

    println("Even Numbers:")
    evenNumbers.collect().foreach(println)

    // ==================================================
    // 4. FLATMAP TRANSFORMATION
    // ==================================================

    println("\n========== 4. FLATMAP ==========")

    val sentences = sc.parallelize(
      List(
        "Spark is fast",
        "Scala is powerful",
        "RDD is distributed"
      )
    )

    val words = sentences.flatMap(sentence => sentence.split(" "))

    println("Words:")
    words.collect().foreach(println)

    // ==================================================
    // 5. DISTINCT TRANSFORMATION
    // ==================================================

    println("\n========== 5. DISTINCT ==========")

    val distinctNumbers = numbers.distinct()

    println("Distinct Numbers:")
    distinctNumbers.collect().foreach(println)

    // ==================================================
    // 6. UNION TRANSFORMATION
    // ==================================================

    println("\n========== 6. UNION ==========")

    val rdd1 = sc.parallelize(List(10, 20, 30))
    val rdd2 = sc.parallelize(List(40, 50, 60))

    val combinedRDD = rdd1.union(rdd2)

    println("Combined RDD:")
    combinedRDD.collect().foreach(println)

    // ==================================================
    // 7. COUNT ACTION
    // ==================================================

    println("\n========== 7. COUNT ==========")

    val numberCount = numbers.count()

    println(s"Number of Elements : $numberCount")

    // ==================================================
    // 8. TAKE ACTION
    // ==================================================

    println("\n========== 8. TAKE ==========")

    val firstThree = numbers.take(3)

    println("First 3 Elements:")
    firstThree.foreach(println)

    // ==================================================
    // 9. FIRST ACTION
    // ==================================================

    println("\n========== 9. FIRST ==========")

    val firstNumber = numbers.first()

    println(s"First Number : $firstNumber")

    // ==================================================
    // 10. REDUCE ACTION
    // ==================================================

    println("\n========== 10. REDUCE ==========")

    val total = numbers.reduce((a, b) => a + b)

    println(s"Sum of All Numbers : $total")

    // ==================================================
    // 11. LOG ANALYZER
    // ==================================================

    println("\n========== 11. LOG ANALYZER ==========")

    val logRDD = sc.textFile("data/application.log")

    println(s"Total Log Lines : ${logRDD.count()}")

    // Count ERROR logs
    val errorLogs = logRDD.filter(line => line.startsWith("ERROR"))

    println(s"ERROR Logs : ${errorLogs.count()}")

    println("ERROR Messages:")
    errorLogs.collect().foreach(println)

    // Count WARN logs
    val warningLogs = logRDD.filter(line => line.startsWith("WARN"))

    println(s"WARN Logs : ${warningLogs.count()}")

    println("WARN Messages:")
    warningLogs.collect().foreach(println)

    // Count INFO logs
    val infoLogs = logRDD.filter(line => line.startsWith("INFO"))

    println(s"INFO Logs : ${infoLogs.count()}")

    // ==================================================
    // 12. LAZY EVALUATION DEMO
    // ==================================================

    println("\n========== 12. LAZY EVALUATION ==========")

    val lazyRDD = numbers
      .map(x => x * 10)
      .filter(x => x > 30)

    println("Transformation created.")
    println("No execution happens until an action is called.")

    println("Calling collect() now:")

    lazyRDD.collect().foreach(println)

    // ==================================================
    // 13. PARTITIONS
    // ==================================================

    println("\n========== 13. PARTITIONS ==========")

    println(s"Number RDD Partitions : ${numbers.getNumPartitions}")
    println(s"Log RDD Partitions    : ${logRDD.getNumPartitions}")
    println(s"Default Parallelism   : ${sc.defaultParallelism}")

    // ==================================================
    // Completion
    // ==================================================

    spark.stop()

    println("\n========================================")
    println("Day 5 completed successfully!")
    println("Aditya completed the Transformations and Actions practice.")
    println("========================================")
  }
}
