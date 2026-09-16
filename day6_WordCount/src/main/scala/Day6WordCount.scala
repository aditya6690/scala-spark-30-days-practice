import org.apache.spark.sql.SparkSession

object Day6WordCount {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("       DAY 6 - WORD COUNT")
    println("========================================")

    val spark = SparkSession.builder()
      .appName("Day6WordCount")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")

    println("\n========== 1. READ TEXT FILE ==========")

    val lines = sc.textFile("data/input.txt")

    println("Input Data:")
    lines.collect().foreach(println)

    println("\n========== 2. FLATMAP ==========")

    val words = lines.flatMap(line => line.split(" "))

    println("Words:")
    words.collect().foreach(println)

    println("\n========== 3. MAP TO KEY-VALUE ==========")

    val wordPairs = words.map(word => (word.toLowerCase, 1))

    println("Word Pairs:")
    wordPairs.collect().foreach(println)

    println("\n========== 4. REDUCE BY KEY ==========")

    val wordCounts = wordPairs.reduceByKey((a, b) => a + b)

    println("Word Counts:")
    wordCounts.collect().sortBy(_._1).foreach(println)

    println("\n========== 5. SORT WORD COUNTS ==========")

    val sortedWordCounts =
      wordCounts.sortBy(_._2, ascending = false)

    println("Words Sorted by Count:")
    sortedWordCounts.collect().foreach(println)

    println("\n========== 6. PARTITIONS ==========")

    println(s"Input RDD Partitions : ${lines.getNumPartitions}")
    println(s"Words RDD Partitions : ${words.getNumPartitions}")
    println(s"Default Parallelism  : ${sc.defaultParallelism}")

    spark.stop()

    println("\n========================================")
    println("Day 6 completed successfully!")
    println("Aditya completed the Word Count practice.")
    println("========================================")
  }
}
