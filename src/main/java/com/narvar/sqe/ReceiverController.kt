package com.narvar.sqe

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("/")
open class ReceiverController {

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("")
  fun rootDirectory() = "Welcome to Receiver App"

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("health_check", method = arrayOf(RequestMethod.GET))
  fun healthCheck() = "OK"

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("tracking_numbers", method = arrayOf(RequestMethod.POST))
  fun receiveNumbers(@RequestBody body: NumbersHolder) {
    File("src/main/resources/numbers.json").writeText(
        text = jacksonObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(body)
    )
  }
}

