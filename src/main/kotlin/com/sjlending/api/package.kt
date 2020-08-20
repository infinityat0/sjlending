package com.sjlending.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson = GsonBuilder().setPrettyPrinting().create()

// This is an augmented method on String class that converts a json-string to a given type
inline fun <reified T> String.parse(): T = gson.fromJson(this, T::class.java)

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
)

data class SSN(val value: String) {
  override fun toString() = "XXX-XX-XXXX"
}

data class Address(
    val street: String,
    val city: String,
    val zipCode: String,
    val state: String
)