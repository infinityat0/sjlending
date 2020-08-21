package com.sjlending.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.apache.logging.log4j.LogManager

@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)  // TODO: Change this to [SecurityRule.IS_AUTHENTICATED] when we have something
class ApiController(private val service: CustomerService) {

  companion object {
    private val logger = LogManager.getLogger(this.javaClass.name)
  }

  @Put("/customer")
  @Produces(MediaType.TEXT_PLAIN)
  fun putCustomer(@Body jsonBody: String): HttpResponse<*> = newCustomer(jsonBody)

  @Post("/customer")
  @Produces(MediaType.TEXT_PLAIN)
  fun newCustomer(@Body jsonBody: String): HttpResponse<*> =
      execute {
        logger.ifDebug { "Creating a new customer" }

        val customer = jsonBody.parse<Customer>()
        service.create(customer)

        logger.ifDebug { "customer creation successful" }
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