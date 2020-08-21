package com.sjlending.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.vertx.reactivex.sqlclient.Tuple
import org.apache.logging.log4j.Logger
import java.sql.Date

val gson: Gson = GsonBuilder().setPrettyPrinting().create()

// This is an augmented method on String class that converts a json-string to a given type
inline fun <reified T> String.parse(): T = gson.fromJson(this, T::class.java)

fun Logger.ifDebug(msg: () -> String) {
  if (this.isDebugEnabled) {
    this.debug(msg)
  }
}

fun Logger.ifInfo(msg: () -> String) {
  if (this.isInfoEnabled) {
    this.info(msg)
  }
}

fun now() = System.currentTimeMillis()

// TODO (sujji): Add any of the attributes
// that you want here and you are good to go
data class Customer(
    val firstName: String,
    val lastName: String,
    val email: String,
    val ssn: SSN,
    val address: Address,
    val phoneNumber: String,
    val borrowAmount: Double,
    val message: String
) {

  fun insertQuery(table: String): String =
    """
      |INSERT INTO $table
      | (first_name, last_name, ssn, borrow_amount, phone, email, street,
      |  city, state, zip_code, message, created_at, modified_at)
      | VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.trimMargin("|")

  fun values(): Tuple = Tuple.wrap(
      listOf(
          firstName, lastName, ssn.value, borrowAmount, phoneNumber, email,
          address.street, address.city, address.state, address.zipCode, message, Date(now()), Date(now())
      )
  )
}

data class SSN(val value: String) {
  override fun toString() = "XXX-XX-XXXX"
}

data class Address(
    val street: String,
    val city: String,
    val zipCode: String,
    val state: String
)