import org.apache.spark.sql.SparkSession

object Day4RDDCreation {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("       DAY 4 - RDD CREATION")
    println("========================================")

    val spark = SparkSession.builder()
      .appName("Day4RDDCreation")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")


    // ==================================================
    // 1. Create RDD From Collection
    // ==================================================

    println("\n========== 1. RDD FROM COLLECTION ==========")

    val marks = List(85,72,91,64,55,78,88)

    val marksRDD = sc.parallelize(marks)

    println("Original Marks")

    marksRDD.collect().foreach(println)

    println(s"Number of Partitions : ${marksRDD.getNumPartitions}")


    // ==================================================
    // 2. MAP Transformation
    // ==================================================

    println("\n========== 2. MAP TRANSFORMATION ==========")

    val updatedMarksRDD = marksRDD.map(mark => mark + 5)

    println("Marks After Adding Bonus")

    updatedMarksRDD.collect().foreach(println)


    // ==================================================
    // 3. FILTER Transformation
    // ==================================================

    println("\n========== 3. FILTER TRANSFORMATION ==========")

    val passedRDD = updatedMarksRDD.filter(mark => mark >= 70)

    println("Passed Students Marks")

    passedRDD.collect().foreach(println)


    // ==================================================
    // 4. flatMap Transformation
    // ==================================================

    println("\n========== 4. FLATMAP TRANSFORMATION ==========")

    val sentences = List(
      "Apache Spark is fast",
      "Scala is powerful",
      "RDD supports distributed processing"
    )

    val sentenceRDD = sc.parallelize(sentences)

    val wordsRDD = sentenceRDD.flatMap(sentence => sentence.split(" "))

    println("Words Generated Using flatMap")

    wordsRDD.collect().foreach(println)


    // ==================================================
    // 5. RDD From Text File
    // ==================================================

    println("\n========== 5. RDD FROM TEXT FILE ==========")

    val salesRDD = sc.textFile("data/sales.txt")

    println("Sales File Content")

    salesRDD.collect().foreach(println)


    // ==================================================
    // 6. MAP Sales Records
    // ==================================================

    println("\n========== 6. SALES RECORDS ==========")

    val salesDataRDD = salesRDD.map(line => {

      val columns = line.split(" ")

      val product = columns(0)
      val amount = columns(1).toDouble

      (product, amount)

    })

    salesDataRDD.collect().foreach(println)


    // ==================================================
    // 7. FILTER High Value Sales
    // ==================================================

    println("\n========== 7. FILTER HIGH VALUE SALES ==========")

    val highSalesRDD =
      salesDataRDD.filter(record => record._2 >= 10000)

    highSalesRDD.collect().foreach(println)


    // ==================================================
    // 8. Total Sales Using reduce
    // ==================================================

    println("\n========== 8. TOTAL SALES ==========")

    val totalSales =
      salesDataRDD
        .map(record => record._2)
        .reduce((x,y) => x+y)

    println(s"Total Sales Amount : ₹$totalSales")


    // ==================================================
    // 9. Product Wise Sales
    // ==================================================

    println("\n========== 9. PRODUCT WISE SALES ==========")

    val productSalesRDD =
      salesDataRDD.reduceByKey((a,b)=>a+b)

    productSalesRDD.collect().foreach(println)


    // ==================================================
    // 10. Partitions and Parallelism
    // ==================================================

    println("\n========== 10. PARTITIONS ==========")

    println(s"Sales RDD Partitions : ${salesRDD.getNumPartitions}")

    println(s"Default Parallelism  : ${sc.defaultParallelism}")


    // ==================================================
    // Completion
    // ==================================================

    spark.stop()

    println("\n========================================")
    println("Day 4 completed successfully!")
    println("Aditya completed the RDD Creation Practice.")
    println("========================================")

  }

}

