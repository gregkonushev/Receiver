package com.narvar.sqe

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MainApplication {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(MainApplication::class.java, *args)
    }
  }
}