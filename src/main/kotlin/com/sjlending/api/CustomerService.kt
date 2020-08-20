package com.sjlending.api

import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import java.util.logging.Logger
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
// @Requirements(Requires(beans = [DataSource::class]), Requires(property = "datasource.default.url"))
class CustomerService() {

  companion object {
    private val logger = Logger.getLogger(this.javaClass.name)
  }

  fun createNewCustomer(customer: Customer) {

  }
}