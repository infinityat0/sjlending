package com.sjlending.api

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import javax.inject.Inject

@MicronautTest
class ApiControllerTest(
    private val application: EmbeddedApplication<*>,
    @Inject @Client("/") private val client: RxHttpClient
) : StringSpec({

    "test the server is running" {
        assert(application.isRunning)
    }

    "test controller takes in a json" {
        val customer = makeCustomer("Sunny", "Vardhan")
        val body = gson.toJson(customer)
        val request: HttpRequest<String> = HttpRequest.POST("/customer/new", body)
        val response: HttpResponse<String> = client.toBlocking().exchange(request)
        response.status shouldBe HttpStatus.OK
    }
})

fun makeCustomer(firstName: String, lastName: String): Customer =
    Customer(
        firstName = firstName,
        lastName = lastName,
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