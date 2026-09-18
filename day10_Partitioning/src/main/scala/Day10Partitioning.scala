import org.apache.spark.sql.SparkSession

object Day10Partitioning {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("        DAY 10 - PARTITIONING")
    println("========================================")

    val spark = SparkSession.builder()
      .appName("Day10Partitioning")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")

    // --------------------------------------------
    // 1. READ INPUT DATA
    // --------------------------------------------

    println("\n========== 1. READ INPUT DATA ==========")

    val numbersRDD = sc.textFile("data/numbers.txt")

    println("Input Data:")
    numbersRDD.collect().foreach(println)

    println(s"Initial Partitions : ${numbersRDD.getNumPartitions}")

    // --------------------------------------------
    // 2. VIEW DATA BY PARTITION
    // --------------------------------------------

    println("\n========== 2. DATA BY PARTITION ==========")

    val partitionData = numbersRDD.mapPartitionsWithIndex {
      case (partitionId, values) =>
        Iterator(
          s"Partition $partitionId -> ${values.toList.mkString(", ")}"
        )
    }

    partitionData.collect().foreach(println)

    // --------------------------------------------
    // 3. REPARTITION
    // --------------------------------------------

    println("\n========== 3. REPARTITION ==========")

    val repartitionedRDD = numbersRDD.repartition(4)

    println(s"Partitions after repartition : ${repartitionedRDD.getNumPartitions}")

    val repartitionData = repartitionedRDD.mapPartitionsWithIndex {
      case (partitionId, values) =>
        Iterator(
          s"Partition $partitionId -> ${values.toList.mkString(", ")}"
        )
    }

    repartitionData.collect().foreach(println)

    // --------------------------------------------
    // 4. COALESCE
    // --------------------------------------------

    println("\n========== 4. COALESCE ==========")

    val coalescedRDD = repartitionedRDD.coalesce(2)

    println(s"Partitions after coalesce : ${coalescedRDD.getNumPartitions}")

    val coalesceData = coalescedRDD.mapPartitionsWithIndex {
      case (partitionId, values) =>
        Iterator(
          s"Partition $partitionId -> ${values.toList.mkString(", ")}"
        )
    }

    coalesceData.collect().foreach(println)

    // --------------------------------------------
    // 5. REPARTITION VS COALESCE
    // --------------------------------------------

    println("\n========== 5. REPARTITION VS COALESCE ==========")

    println("repartition(4) -> increases partitions to 4")
    println("coalesce(2)    -> reduces partitions to 2")

    println("repartition can cause a shuffle.")
    println("coalesce is mainly used to reduce partitions.")

    // --------------------------------------------
    // 6. PARTITION COUNT SUMMARY
    // --------------------------------------------

    println("\n========== 6. PARTITION SUMMARY ==========")

    println(s"Initial RDD Partitions       : ${numbersRDD.getNumPartitions}")
    println(s"Repartitioned RDD Partitions : ${repartitionedRDD.getNumPartitions}")
    println(s"Coalesced RDD Partitions     : ${coalescedRDD.getNumPartitions}")
    println(s"Default Parallelism          : ${sc.defaultParallelism}")

    // --------------------------------------------
    // 7. ACTION
    // --------------------------------------------

    println("\n========== 7. ACTION ==========")

    println(s"Total Records: ${numbersRDD.count()}")

    spark.stop()

    println("\n========================================")
    println("Day 10 completed successfully!")
    println("Aditya completed Partitioning practice.")
    println("========================================")
  }
}
