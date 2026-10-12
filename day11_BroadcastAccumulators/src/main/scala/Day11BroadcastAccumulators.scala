import org.apache.spark.{SparkConf, SparkContext}

object Day11BroadcastAccumulators {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("Day11-Broadcast-Accumulators")
      .setMaster("local[*]")

    val sc = new SparkContext(conf)

    println("==============================================")
    println("   DAY 11 - BROADCAST AND ACCUMULATORS")
    println("==============================================")

    // --------------------------------------------------
    // 1. Small product master/reference table
    // --------------------------------------------------
    val productMaster = Map(
      "P001" -> "Laptop",
      "P002" -> "Mouse",
      "P003" -> "Keyboard",
      "P004" -> "Monitor"
    )

    println("\nProduct Master:")
    productMaster.toSeq.sortBy(_._1).foreach {
      case (productId, productName) =>
        println(s"$productId -> $productName")
    }

    // --------------------------------------------------
    // 2. Broadcast the product reference map
    // --------------------------------------------------
    val broadcastProducts = sc.broadcast(productMaster)

    println("\nBroadcast variable created successfully.")

    // --------------------------------------------------
    // 3. Accumulator for bad/invalid records
    // --------------------------------------------------
    val badRecordAccumulator =
      sc.longAccumulator("Bad Records")

    // --------------------------------------------------
    // 4. Read transaction data
    // --------------------------------------------------
    val transactionsRDD =
      sc.textFile("data/transactions.txt")

    // --------------------------------------------------
    // 5. Validate transactions using Broadcast
    // --------------------------------------------------
    val validatedTransactions = transactionsRDD.map { line =>

      val parts = line.split("\\s+")

      val transactionId = parts(0)
      val productId = parts(1)
      val amount = parts(2).toDouble

      // Use broadcast product master on executor
      broadcastProducts.value.get(productId) match {

        case Some(productName) =>
          (
            transactionId,
            productId,
            productName,
            amount,
            "VALID"
          )

        case None =>
          badRecordAccumulator.add(1)

          (
            transactionId,
            productId,
            "UNKNOWN",
            amount,
            "INVALID"
          )
      }
    }

    // Cache so the accumulator is not updated again
    // when we perform subsequent actions.
    validatedTransactions.cache()

    // Materialize the RDD once.
    val totalRecords = validatedTransactions.count()

    println(s"\nTotal Records : $totalRecords")
    println(s"Bad Records   : ${badRecordAccumulator.value}")

    // --------------------------------------------------
    // 6. Display validation results
    // --------------------------------------------------
    println("\nValidation Results:")
    println("----------------------------------------------")

    validatedTransactions.collect().foreach {
      case (transactionId, productId, productName, amount, status) =>
        println(
          f"$transactionId%-5s $productId%-5s $productName%-10s " +
          f"$amount%8.2f  $status"
        )
    }

    // --------------------------------------------------
    // 7. Valid transaction processing
    // --------------------------------------------------
    val validTransactions =
      validatedTransactions.filter(_._5 == "VALID")

    println("\nValid Transactions:")
    println("----------------------------------------------")

    validTransactions.collect().foreach {
      case (transactionId, productId, productName, amount, status) =>
        println(
          f"$transactionId%-5s $productId%-5s $productName%-10s " +
          f"$amount%8.2f"
        )
    }

    // --------------------------------------------------
    // 8. Aggregate valid transactions by product
    // --------------------------------------------------
    val productTotals = validTransactions
      .map {
        case (_, productId, productName, amount, _) =>
          (productId, (productName, amount))
      }
      .mapValues {
        case (productName, amount) =>
          (productName, amount)
      }
      .reduceByKey {
        case ((name1, amount1), (_, amount2)) =>
          (name1, amount1 + amount2)
      }
      .collect()
      .sortBy(_._1)

    println("\nValid Transaction Total by Product:")
    println("----------------------------------------------")

    productTotals.foreach {
      case (productId, (productName, totalAmount)) =>
        println(
          f"$productId%-5s $productName%-10s $totalAmount%10.2f"
        )
    }

    // --------------------------------------------------
    // 9. Overall statistics
    // --------------------------------------------------
    val validCount = validTransactions.count()
    val invalidCount = totalRecords - validCount

    val totalValidAmount =
      productTotals.map(_._2._2).sum

    println("\nOverall Statistics:")
    println("----------------------------------------------")
    println(s"Total Transactions : $totalRecords")
    println(s"Valid Transactions : $validCount")
    println(s"Invalid Transactions : $invalidCount")
    println(f"Total Valid Amount : $totalValidAmount%.2f")
    println(s"Bad Record Accumulator : ${badRecordAccumulator.value}")

    // --------------------------------------------------
    // 10. Why normal driver variables should not be used
    // --------------------------------------------------
    println("\nWhy Use an Accumulator?")
    println("----------------------------------------------")
    println(
      "Normal driver variables should not be used for distributed " +
      "updates because executors run tasks independently."
    )
    println(
      "Accumulator variables are designed for safely collecting " +
      "distributed counters back to the driver."
    )

    println("\nWhy Use Broadcast?")
    println("----------------------------------------------")
    println(
      "Broadcast sends the small product reference data efficiently " +
      "to executors instead of repeatedly sending it with task data."
    )

    println("\n==============================================")
    println("Day 11 execution completed successfully.")
    println("==============================================")

    broadcastProducts.destroy()
    sc.stop()
  }
}
