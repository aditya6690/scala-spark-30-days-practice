import org.apache.spark.sql.SparkSession

object Day3SparkSetup {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("       DAY 3 - SPARK SETUP")
    println("========================================")

    // Get number of local cores from command-line argument.
    // If no argument is provided, use all available cores.
    val cores = if (args.nonEmpty) args(0) else "*"

    // Create SparkSession
    val spark = SparkSession.builder()
      .appName("Day3SparkSetup")
      .master(s"local[$cores]")
      .getOrCreate()

    // Reduce Spark logging
    spark.sparkContext.setLogLevel("WARN")

    println(s"\nSpark Version: ${spark.version}")
    println(s"Application Name: ${spark.sparkContext.appName}")
    println(s"Master: ${spark.sparkContext.master}")


    // --------------------------------------------------
    // Spark Context
    // --------------------------------------------------

    println("\n--- Spark Context ---")

    val sc = spark.sparkContext

    println(s"Application ID: ${sc.applicationId}")
    println(s"Default Parallelism: ${sc.defaultParallelism}")


    // --------------------------------------------------
    // Read Text File
    // --------------------------------------------------

    println("\n--- Reading Text File ---")

    val filePath = "data/sample.txt"

    val lines = sc.textFile(filePath)

    lines.collect().foreach(println)

    println(s"\nNumber of Lines: ${lines.count()}")


    // --------------------------------------------------
    // Stop Spark
    // --------------------------------------------------

    spark.stop()

    println("\n========================================")
    println("Day 3 completed successfully!")
    println("Spark application executed successfully.")
    println("========================================")
  }
}
