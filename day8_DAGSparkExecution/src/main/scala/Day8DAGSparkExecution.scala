import org.apache.spark.sql.SparkSession

object Day8DAGSparkExecution {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("       DAY 8 - DAG AND SPARK EXECUTION")
    println("========================================")

    val spark = SparkSession.builder()
      .appName("Day8DAGSparkExecution")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")

    // --------------------------------------------
    // 1. CREATE INPUT RDD
    // --------------------------------------------

    println("\n========== 1. CREATE INPUT RDD ==========")

    val lines = sc.textFile("data/input.txt")

    println("Input Data:")
    lines.collect().foreach(println)

    // --------------------------------------------
    // 2. TRANSFORMATION CHAIN
    // --------------------------------------------

    println("\n========== 2. TRANSFORMATION CHAIN ==========")

    val words = lines.flatMap(_.split(" "))

    val filteredWords = words.filter(_.nonEmpty)

    val wordPairs = filteredWords.map(word => (word.toLowerCase, 1))

    println("Transformations created:")
    println("textFile")
    println("   ↓")
    println("flatMap")
    println("   ↓")
    println("filter")
    println("   ↓")
    println("map")
    println("   ↓")
    println("reduceByKey")

    // --------------------------------------------
    // 3. REDUCE BY KEY
    // --------------------------------------------

    println("\n========== 3. REDUCE BY KEY ==========")

    val wordCounts = wordPairs.reduceByKey(_ + _)

    println("Word Counts:")
    wordCounts.collect().sortBy(_._1).foreach(println)

    // --------------------------------------------
    // 4. DAG / LINEAGE
    // --------------------------------------------

    println("\n========== 4. DAG / RDD LINEAGE ==========")

    println("RDD Lineage:")
    println(wordCounts.toDebugString)

    println("\nDAG Flow:")
    println("Input RDD")
    println("   ↓")
    println("flatMap")
    println("   ↓")
    println("filter")
    println("   ↓")
    println("map")
    println("   ↓")
    println("reduceByKey")
    println("   ↓")
    println("Action: collect()")

    // --------------------------------------------
    // 5. EXECUTION FLOW
    // --------------------------------------------

    println("\n========== 5. SPARK EXECUTION FLOW ==========")

    println("1. Driver program creates the Spark application.")
    println("2. Transformations build the execution plan.")
    println("3. An action triggers execution.")
    println("4. Spark creates stages from the DAG.")
    println("5. Stages are divided into tasks.")
    println("6. Tasks execute on available partitions.")

    // --------------------------------------------
    // 6. PARTITION INFORMATION
    // --------------------------------------------

    println("\n========== 6. PARTITIONS ==========")

    println(s"Input RDD Partitions : ${lines.getNumPartitions}")
    println(s"Word RDD Partitions  : ${words.getNumPartitions}")
    println(s"Result RDD Partitions: ${wordCounts.getNumPartitions}")
    println(s"Default Parallelism  : ${sc.defaultParallelism}")

    // --------------------------------------------
    // 7. RESULT
    // --------------------------------------------

    println("\n========== 7. FINAL RESULT ==========")

    println(s"Total unique words: ${wordCounts.count()}")

    spark.stop()

    println("\n========================================")
    println("Day 8 completed successfully!")
    println("Aditya completed DAG and Spark Execution.")
    println("========================================")
  }
}
