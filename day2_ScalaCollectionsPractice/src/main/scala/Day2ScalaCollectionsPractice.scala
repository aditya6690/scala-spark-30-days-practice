object Day2ScalaCollectionsPractice {

  def main(args: Array[String]): Unit = {

    println("========================================")
    println("      DAY 2 - SCALA COLLECTIONS")
    println("========================================")


    // --------------------------------------------------
    // 1. Sales List - map, filter and reduce
    // --------------------------------------------------

    println("\n--- Sales List ---")

    val sales = List(1200.0, 850.0, 450.0, 2200.0, 1500.0)

    println(s"Original Sales: $sales")

    // map: apply 10% discount
    val discountedSales = sales.map(amount => amount * 0.90)

    println(s"Sales after 10% discount: $discountedSales")

    // filter: select sales greater than 1000
    val highValueSales = sales.filter(amount => amount > 1000)

    println(s"Sales greater than 1000: $highValueSales")

    // reduce: calculate total sales
    val totalSales = sales.reduce((a, b) => a + b)

    println(s"Total Sales: $totalSales")


    // --------------------------------------------------
    // 2. flatMap Example
    // --------------------------------------------------

    println("\n--- flatMap Example ---")

    val dailySales = List(
      List(1000.0, 500.0),
      List(750.0, 250.0),
      List(1200.0)
    )

    val flattenedSales = dailySales.flatMap(day => day)

    println(s"Flattened Sales: $flattenedSales")

    val flattenedTotal = flattenedSales.reduce((a, b) => a + b)

    println(s"Flattened Total: $flattenedTotal")


    // --------------------------------------------------
    // 3. Vector - Indexed Customer Records
    // --------------------------------------------------

    println("\n--- Vector - Indexed Customer Records ---")

    val customers = Vector(
      (101, "Aditya"),
      (102, "Rahul"),
      (103, "Priya"),
      (104, "Sneha")
    )

    println(s"Customer at index 0: ${customers(0)}")
    println(s"Customer at index 1: ${customers(1)}")
    println(s"Customer at index 2: ${customers(2)}")

    println(s"Total Customers: ${customers.size}")


    // --------------------------------------------------
    // 4. Map - Product Quantities and Prices
    // --------------------------------------------------

    println("\n--- Map - Product Quantities and Prices ---")

    val productQuantities = Map(
      "Laptop" -> 2,
      "Mouse" -> 5,
      "Keyboard" -> 3,
      "Monitor" -> 2
    )

    val productPrices = Map(
      "Laptop" -> 50000.0,
      "Mouse" -> 800.0,
      "Keyboard" -> 1500.0,
      "Monitor" -> 12000.0
    )

    val productSales = productQuantities.map {
      case (product, quantity) =>
        val price = productPrices(product)
        val salesAmount = quantity * price

        (product, quantity, price, salesAmount)
    }

    productSales.foreach {
      case (product, quantity, price, amount) =>
        println(
          f"$product%-10s -> Quantity: $quantity, Price: $price%.1f, Sales: $amount%.1f"
        )
    }

    val productSalesTotal =
      productSales.map {
        case (_, _, _, amount) => amount
      }.reduce((a, b) => a + b)

    println(s"Product Sales Total: $productSalesTotal")


    // --------------------------------------------------
    // 5. For-Comprehension - Customers and Orders
    // --------------------------------------------------

    println("\n--- For-Comprehension: Customers and Orders ---")

    val customerRecords = List(
      (101, "Aditya"),
      (102, "Rahul"),
      (103, "Priya"),
      (104, "Sneha")
    )

    val orders = List(
      (101, "Laptop", 1),
      (101, "Mouse", 2),
      (102, "Keyboard", 1),
      (103, "Monitor", 1),
      (104, "Mouse", 3)
    )

    val customerOrders = for {
      (customerId, customerName) <- customerRecords
      (orderCustomerId, product, quantity) <- orders
      if customerId == orderCustomerId
    } yield {
      val price = productPrices(product)
      val amount = quantity * price

      (customerName, product, quantity, amount)
    }

    customerOrders.foreach {
      case (name, product, quantity, amount) =>
        println(
          f"$name ordered $product, Quantity: $quantity, Amount: $amount%.1f"
        )
    }


    // --------------------------------------------------
    // 6. Daily Sales Summary
    // --------------------------------------------------

    println("\n--- Daily Sales Summary ---")

    val orderAmounts = customerOrders.map {
      case (_, _, _, amount) => amount
    }

    val numberOfOrders = orderAmounts.size

    val dailyTotalSales =
      orderAmounts.reduce((a, b) => a + b)

    val averageOrderValue =
      dailyTotalSales / numberOfOrders

    val highestOrderValue =
      orderAmounts.max

    val lowestOrderValue =
      orderAmounts.min

    println(s"Number of Orders: $numberOfOrders")
    println(f"Total Daily Sales: $dailyTotalSales%.1f")
    println(f"Average Order Value: $averageOrderValue%.2f")
    println(f"Highest Order Value: $highestOrderValue%.1f")
    println(f"Lowest Order Value: $lowestOrderValue%.1f")


    // --------------------------------------------------
    // 7. Product-wise Sales Summary
    // --------------------------------------------------

    println("\n--- Product-wise Sales ---")

    val productWiseSales =
      customerOrders
        .groupBy {
          case (_, product, _, _) => product
        }
        .map {
          case (product, records) =>
            val total =
              records.map {
                case (_, _, _, amount) => amount
              }.reduce((a, b) => a + b)

            (product, total)
        }

    productWiseSales.toSeq
      .sortBy(_._1)
      .foreach {
        case (product, amount) =>
          println(f"$product%-10s -> $amount%.1f")
      }


    // --------------------------------------------------
    // 8. Completion
    // --------------------------------------------------

    println("\n========================================")
    println("Day 2 completed successfully!")
    println("Aditya completed the Scala Collections Practice.")
    println("========================================")
  }
}

