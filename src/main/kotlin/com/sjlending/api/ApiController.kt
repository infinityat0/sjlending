package com.sjlending.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.util.logging.Level
import java.util.logging.Logger

@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)  // TODO: Change this to [SecurityRule.IS_AUTHENTICATED] when we have something
class ApiController(private val service: CustomerService) {

  companion object {
    private val logger = Logger.getLogger(this.javaClass.name)
  }

  @Post("/customer/new")
  @Produces(MediaType.TEXT_PLAIN)
  fun newCustomer(@Body jsonBody: String): HttpResponse<*> =
      execute {
        logger.log(Level.FINE) { "Creating a new customer" }

        val customer = jsonBody.parse<Customer>()
        service.create(customer)

        logger.log(Level.FINE) { "customer creation successful" }
      }

  private fun execute(block: () -> Unit): HttpResponse<*> =
      try {
        block()
        HttpResponse.ok("")
      } catch (ex: Exception) {
        ex.printStackTrace()
        logger.ifInfo { "Failed creating a customer: ex=$ex" }
        HttpResponse.serverError("Failed creating a customer")
      }
}