import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel

object Day12CachePersist {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("Day12-Cache-Persist")
      .setMaster("local[*]")

    val sc = new SparkContext(conf)

    println("==============================================")
    println("       DAY 12 - CACHE AND PERSIST")
    println("==============================================")

    // --------------------------------------------------
    // 1. Read raw transaction data
    // --------------------------------------------------
    val rawTransactions =
      sc.textFile("data/transactions.txt")

    println("\nRaw Transaction Count : " + rawTransactions.count())

    // --------------------------------------------------
    // 2. Clean the transaction dataset
    // --------------------------------------------------
    val cleanedTransactions = rawTransactions
      .map(_.trim)
      .filter(_.nonEmpty)
      .map { line =>
        val parts = line.split("\\s+")

        val transactionId = parts(0)
        val customerId = parts(1)
        val productId = parts(2)
        val amount = parts(3).toDouble

        (transactionId, customerId, productId, amount)
      }
      .filter {
        case (_, _, _, amount) =>
          amount > 0
      }

    println("\nCleaned Transaction Dataset Created.")

    // --------------------------------------------------
    // 3. Cache the cleaned RDD
    // --------------------------------------------------
    cleanedTransactions.cache()

    println("\nCache Status:")
    println("cleanedTransactions.cache() applied.")
    println("Default cache storage level: MEMORY_ONLY")

    // Materialize the cached RDD.
    val cleanedCount = cleanedTransactions.count()

    println("Cleaned Transaction Count : " + cleanedCount)

    // --------------------------------------------------
    // Report 1 - Customer-wise Sales
    // --------------------------------------------------
    println("\n==============================================")
    println("REPORT 1 - CUSTOMER-WISE SALES")
    println("==============================================")

    val customerSales = cleanedTransactions
      .map {
        case (_, customerId, _, amount) =>
          (customerId, amount)
      }
      .reduceByKey(_ + _)
      .collect()
      .sortBy(_._1)

    customerSales.foreach {
      case (customerId, totalAmount) =>
        println(f"$customerId%-5s -> $totalAmount%.2f")
    }

    // --------------------------------------------------
    // Report 2 - Product-wise Sales
    // --------------------------------------------------
    println("\n==============================================")
    println("REPORT 2 - PRODUCT-WISE SALES")
    println("==============================================")

    val productSales = cleanedTransactions
      .map {
        case (_, _, productId, amount) =>
          (productId, amount)
      }
      .reduceByKey(_ + _)
      .collect()
      .sortBy(_._1)

    productSales.foreach {
      case (productId, totalAmount) =>
        println(f"$productId%-5s -> $totalAmount%.2f")
    }

    // --------------------------------------------------
    // Report 3 - Overall Sales
    // --------------------------------------------------
    println("\n==============================================")
    println("REPORT 3 - OVERALL SALES")
    println("==============================================")

    val totalTransactions = cleanedTransactions.count()

    val totalSales = cleanedTransactions
      .map {
        case (_, _, _, amount) =>
          amount
      }
      .reduce(_ + _)

    val averageSale = totalSales / totalTransactions

    val maximumSale = cleanedTransactions
      .map {
        case (_, _, _, amount) =>
          amount
      }
      .max()

    println(s"Total Transactions : $totalTransactions")
    println(f"Total Sales       : $totalSales%.2f")
    println(f"Average Sale      : $averageSale%.2f")
    println(f"Maximum Sale      : $maximumSale%.2f")

    // --------------------------------------------------
    // 4. Compare cache() and persist()
    // --------------------------------------------------
    println("\n==============================================")
    println("CACHE VS PERSIST")
    println("==============================================")

    println("cache()  -> Equivalent to persist(StorageLevel.MEMORY_ONLY)")
    println("persist() -> Allows selecting a storage level.")

    println("\nExample storage levels:")
    println("MEMORY_ONLY")
    println("MEMORY_ONLY_SER")
    println("MEMORY_AND_DISK")
    println("DISK_ONLY")

    // --------------------------------------------------
    // 5. Demonstrate persist()
    // --------------------------------------------------
    val persistedTransactions =
      rawTransactions
        .map(_.trim)
        .filter(_.nonEmpty)
        .map { line =>
          val parts = line.split("\\s+")

          (
            parts(0),
            parts(1),
            parts(2),
            parts(3).toDouble
          )
        }
        .filter {
          case (_, _, _, amount) =>
            amount > 0
        }
        .persist(StorageLevel.MEMORY_AND_DISK)

    val persistedCount = persistedTransactions.count()

    println("\nPersist Example:")
    println("Storage Level : MEMORY_AND_DISK")
    println("Persisted Transaction Count : " + persistedCount)

    // --------------------------------------------------
    // 6. When caching can hurt performance
    // --------------------------------------------------
    println("\n==============================================")
    println("WHEN CAN CACHING HURT PERFORMANCE?")
    println("==============================================")

    println("1. When the RDD is used only once.")
    println("2. When the dataset is too large for available memory.")
    println("3. When caching causes memory pressure and eviction.")
    println("4. When recomputation is cheaper than storing the data.")
    println("5. When unnecessary cached datasets consume executor memory.")

    println("\n==============================================")
    println("Day 12 execution completed successfully.")
    println("==============================================")

    // Clean up cached/persisted data.
    cleanedTransactions.unpersist()
    persistedTransactions.unpersist()

    sc.stop()
  }
}
