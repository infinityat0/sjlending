package com.sjlending.api

import io.micronaut.runtime.Micronaut.build

object Application {
  @JvmStatic
  fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.sjlending.api")
        .mainClass(Application.javaClass)
        .start()
  }
}