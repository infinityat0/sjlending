package com.sjlending.api

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class JsonSerializationTest: StringSpec({

  "can dencode a json with gson" {
    val customer = Customer(
        firstName = "Kiran",
        lastName = "Kumar",
        ssn = SSN("420424242"),
        borrowAmount = 1_000_000.0,
        phoneNumber = "5015049067",
        address = Address(
            street = "Piscataway Drive",
            city = "Edison",
            zipCode = "94041",
            state = "NJ"
        ),
        message = "Please Sir, give me some dalara",
        email = "ksagar_k@yahoo.com"
    )
    val jsonString = gson.toJson(customer)
    val decoded = jsonString.parse<Customer>()

    decoded.borrowAmount shouldBe customer.borrowAmount
    decoded.phoneNumber shouldBe customer.phoneNumber
    decoded.firstName shouldBe customer.firstName
    decoded.lastName shouldBe customer.lastName
    decoded.address shouldBe customer.address
    decoded.message shouldBe customer.message
    decoded.email shouldBe customer.email
    decoded.ssn shouldBe customer.ssn
  }
})