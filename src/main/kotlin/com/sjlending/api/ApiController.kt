package com.sjlending.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Controller("/")
@Secured(SecurityRule.IS_ANONYMOUS)  // TODO: Change this to [SecurityRule.IS_AUTHENTICATED] when we have something
class ApiController {

  companion object {

  }

  @Post("/customer/new")
  @Produces(MediaType.TEXT_PLAIN)
  fun newCustomer(@Body jsonBody: String): HttpResponse<*> =
      execute {
        
      }

  private fun execute(block: () -> Unit): HttpResponse<*> =
      try {
        block()
        HttpResponse.ok("")
      } catch (ex: Exception) {
        HttpResponse.serverError("Failed while handling the notification")
      }

}