package com.narvar.sqe

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.File

@Configuration
open class JacksonConfiguration {
  @Bean
  open fun objectMapper(): ObjectMapper = jacksonObjectMapper()
}

@RestController
@RequestMapping("/")
open class NumbersController {

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("health_check", method = arrayOf(RequestMethod.GET))
  fun healthCheck() = "OK"

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("tracking_numbers", method = arrayOf(RequestMethod.POST))
  fun receiveNumbers(@RequestBody body: Incoming) {
    File("src/main/resources/numbers.json").writeText(
        text = jacksonObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(body)
    )
  }
}

data class Incoming(val tracking_numbers: List<NumbersContainer>)
data class NumbersContainer(val status: String, val carrier_name: String, val values: List<String>)