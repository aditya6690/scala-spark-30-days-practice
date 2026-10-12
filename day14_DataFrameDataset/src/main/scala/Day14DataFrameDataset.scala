import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

case class Employee(
  employee_id: String,
  name: String,
  department: String,
  salary: Double,
  bonus: Double
)

case class EmployeePayroll(
  employee_id: String,
  name: String,
  department: String,
  salary: Double,
  bonus: Double,
  total_payroll: Double
)

object Day14DataFrameDataset {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Day14 DataFrame Dataset")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    // --------------------------------------------------
    // 1. Read employee data as DataFrame
    // --------------------------------------------------

    println("\n==============================================")
    println("DAY 14 - DATAFRAME AND DATASET")
    println("==============================================")

    val employeeDF: DataFrame = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/employees.csv")

    println("\n--- Employee DataFrame ---")
    employeeDF.show(false)

    println("\n--- DataFrame Schema ---")
    employeeDF.printSchema()

    // --------------------------------------------------
    // 2. Convert DataFrame -> Dataset
    // --------------------------------------------------

    val employeeDS = employeeDF.as[Employee]

    println("\n--- Employee Dataset ---")
    employeeDS.show(false)

    // --------------------------------------------------
    // 3. Convert Dataset -> DataFrame
    // --------------------------------------------------

    val employeeDF2 = employeeDS.toDF()

    println("\n--- Dataset Converted Back To DataFrame ---")
    employeeDF2.show(false)

    // --------------------------------------------------
    // 4. Typed Employee Payroll Pipeline
    // --------------------------------------------------

    val payrollDS = employeeDS.map { employee =>

      EmployeePayroll(
        employee.employee_id,
        employee.name,
        employee.department,
        employee.salary,
        employee.bonus,
        employee.salary + employee.bonus
      )

    }

    println("\n--- Typed Employee Payroll Dataset ---")
    payrollDS.show(false)

    // --------------------------------------------------
    // 5. Department-wise Payroll
    // --------------------------------------------------

    val departmentPayroll = payrollDS
      .groupBy("department")
      .agg(
        count("employee_id").alias("employees"),
        sum("salary").alias("total_salary"),
        sum("bonus").alias("total_bonus"),
        sum("total_payroll").alias("total_payroll"),
        avg("total_payroll").alias("average_payroll")
      )
      .orderBy(desc("total_payroll"))

    println("\n--- Department-wise Payroll ---")
    departmentPayroll.show(false)

    // --------------------------------------------------
    // 6. Overall Payroll Statistics
    // --------------------------------------------------

    val overallPayroll = payrollDS
      .agg(
        count("employee_id").alias("total_employees"),
        sum("salary").alias("total_salary"),
        sum("bonus").alias("total_bonus"),
        sum("total_payroll").alias("total_payroll"),
        avg("total_payroll").alias("average_payroll"),
        max("total_payroll").alias("maximum_payroll"),
        min("total_payroll").alias("minimum_payroll")
      )

    println("\n--- Overall Payroll Statistics ---")
    overallPayroll.show(false)

    // --------------------------------------------------
    // 7. DataFrame vs Dataset vs RDD
    // --------------------------------------------------

    println("\n==============================================")
    println("RDD vs DATAFRAME vs DATASET")
    println("==============================================")

    println("""
RDD:
- Low-level distributed collection.
- Provides fine-grained control over data.
- Does not provide DataFrame/Dataset schema information.

DataFrame:
- Distributed collection organized into named columns.
- DataFrame is effectively Dataset[Row].
- Supports Spark SQL and Catalyst query optimization.

Dataset:
- Combines structured data processing with strong typing.
- Uses case classes for typed records.
- Provides compile-time type checking for typed operations.

Type Safety:
- Dataset operations using case classes provide stronger compile-time
  type safety than untyped DataFrame Row operations.

Catalyst Optimization:
- Spark SQL/DataFrame/Dataset queries can be optimized by Spark's
  Catalyst query optimizer before execution.
""")

    // --------------------------------------------------
    // 8. Payroll Example
    // --------------------------------------------------

    println("\n==============================================")
    println("PAYROLL PIPELINE SUMMARY")
    println("==============================================")

    println("Input Format       : CSV")
    println("DataFrame Created  : Yes")
    println("Dataset Created    : Yes")
    println("Dataset -> DF      : Yes")
    println("Typed Payroll      : Yes")
    println("Department Report  : Yes")
    println("Overall Report     : Yes")
	 
    println("\n==============================================")
    println("Day 14 execution completed successfully.")
    println("==============================================")
    spark.stop()
  }
}
