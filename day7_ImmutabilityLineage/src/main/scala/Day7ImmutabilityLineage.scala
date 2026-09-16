import org.apache.spark.sql.SparkSession

object Day7ImmutabilityLineage {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println(" DAY 7 - IMMUTABILITY, LINEAGE & FAULT TOLERANCE")
    println("========================================")

    val spark = SparkSession.builder()
      .appName("Day7ImmutabilityLineage")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext

    println(s"Spark Version : ${spark.version}")
    println(s"Application   : ${sc.appName}")
    println(s"Master        : ${sc.master}")

    // ------------------------------------------------
    // 1. CREATE BASE RDD
    // ------------------------------------------------

    println("\n========== 1. BASE RDD ==========")

    val numbersRDD = sc.parallelize(1 to 10)

    println("Original RDD:")
    println(numbersRDD.collect().toList)

    // ------------------------------------------------
    // 2. MULTI-STEP TRANSFORMATION CHAIN
    // ------------------------------------------------

    println("\n========== 2. TRANSFORMATION CHAIN ==========")

    val evenNumbersRDD = numbersRDD.filter(_ % 2 == 0)

    val squaredRDD = evenNumbersRDD.map(x => x * x)

    val resultRDD = squaredRDD.filter(_ > 20)

    println("Even Numbers:")
    println(evenNumbersRDD.collect().toList)

    println("Squared Numbers:")
    println(squaredRDD.collect().toList)

    println("Final Result:")
    println(resultRDD.collect().toList)

    // ------------------------------------------------
    // 3. IMMUTABILITY
    // ------------------------------------------------

    println("\n========== 3. IMMUTABILITY ==========")

    println("Original RDD remains unchanged:")
    println(numbersRDD.collect().toList)

    println("Derived RDD:")
    println(resultRDD.collect().toList)

    println("RDDs are immutable: transformations create new RDDs.")

    // ------------------------------------------------
    // 4. LINEAGE
    // ------------------------------------------------

    println("\n========== 4. RDD LINEAGE ==========")

    println("Lineage of resultRDD:")
    println(resultRDD.toDebugString)

    println("\nTransformation flow:")
    println("numbersRDD")
    println("    |")
    println("    v")
    println("filter(_ % 2 == 0)")
    println("    |")
    println("    v")
    println("map(x => x * x)")
    println("    |")
    println("    v")
    println("filter(_ > 20)")
    println("    |")
    println("    v")
    println("resultRDD")

    // ------------------------------------------------
    // 5. FAULT TOLERANCE
    // ------------------------------------------------

    println("\n========== 5. FAULT TOLERANCE ==========")

    println("If a partition is lost, Spark can recompute")
    println("that partition using the RDD lineage.")

    println("\nConceptual recovery:")
    println("Lost resultRDD partition")
    println("        <- filter(_ > 20)")
    println("        <- map(x => x * x)")
    println("        <- filter(_ % 2 == 0)")
    println("        <- numbersRDD")

    println("\nSpark does not need to recompute unrelated partitions.")
    println("It recomputes the lost partition from its lineage.")

    // ------------------------------------------------
    // 6. PARTITIONS
    // ------------------------------------------------

    println("\n========== 6. PARTITIONS ==========")

    println(s"Original RDD Partitions : ${numbersRDD.getNumPartitions}")
    println(s"Result RDD Partitions   : ${resultRDD.getNumPartitions}")
    println(s"Default Parallelism     : ${sc.defaultParallelism}")

    spark.stop()

    println("\n========================================")
    println("Day 7 completed successfully!")
    println("Aditya completed Immutability, Lineage and Fault Tolerance.")
    println("========================================")
  }
}
