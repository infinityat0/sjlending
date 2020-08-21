package com.sjlending.api

import io.micronaut.context.annotation.Property
import io.vertx.reactivex.mysqlclient.MySQLPool
import io.vertx.reactivex.sqlclient.Row
import org.apache.logging.log4j.LogManager
import javax.inject.Singleton

@Singleton
class CustomerService(
    @Property(name = "datasource.table-name")
    private val table: String,
    private val client: MySQLPool
) {

  companion object {
    private val logger = LogManager.getLogger(this.javaClass.name)
  }

  fun create(customer: Customer) {
    logger.ifDebug { "Creating a new customer: $customer" }
    client
        .preparedQuery(customer.insertQuery(table))
        .rxExecute(customer.values())
        .blockingGet()
  }

  fun getByName(firstName: String, lastName: String): Customer =
      getFromDB("SELECT * FROM $table where first_name=$firstName AND last_name=$lastName")

  fun getBySSN(ssn: SSN): Customer =
      getFromDB("SELECT * FROM $table where ssn=${ssn.value}")

  fun getByEmail(email: String): Customer =
      getFromDB("SELECT * FROM $table where email=$email")

  fun getByPhoneNumber(phoneNumber: String): Customer =
      getFromDB("SELECT * FROM $table where phone_number=$phoneNumber")

  private fun getFromDB(query: String): Customer =
      client
          .query(query)
          .rxExecute()
          .map { rowSet -> makeCustomer(row = rowSet.iterator().next()) }
          .blockingGet()

  private fun makeCustomer(row: Row): Customer =
      Customer(
          firstName = row.getString("first_name"),
          lastName = row.getString("last_name"),
          phoneNumber = row.getString("phone"),
          email = row.getString("email"),
          ssn = SSN(row.getString("ssn")),
          address = Address(
              street = row.getString("street"),
              city = row.getString("city"),
              state = row.getString("state"),
              zipCode = row.getString("zip_code")
          ),
          borrowAmount = row.getDouble("borrow_amount"),
          message = row.getString("message")
      )
}