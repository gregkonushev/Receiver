package com.narvar.sqe.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.narvar.sqe.pojo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@RestController
open class ReceiverController @Autowired constructor(var repo: NumbersRepository, var mapper: ObjectMapper) {

  @ResponseStatus(OK)
  @RequestMapping("/")
  fun rootDirectory() = "Welcome to Receiver App"

  @ResponseStatus(OK)
  @RequestMapping("/health_check", method = arrayOf(RequestMethod.GET))
  fun healthCheck(): String = "OK"

  @ResponseStatus(OK)
  @RequestMapping("/tracking_numbers", method = arrayOf(RequestMethod.PUT))
  fun receiveNumbers(@RequestBody body: Numbers): StandardResponse {
    body.tracking_numbers.forEach {
      repo.saveNumbers(it)
    }
    return StandardResponse(200, "values saved in redis")
  }

  @ResponseStatus(OK)
  @RequestMapping("/tracking_numbers", method = arrayOf(RequestMethod.GET))
  fun getNumbers(@RequestParam queryParameters: Map<String, String>?) : Numbers {
    if (queryParameters?.isEmpty() as Boolean) {
      return repo.getAllNumbers()
    } else {
      return repo.getNumbersByCarrierAndStatus(mapper.convertValue(queryParameters, QueryParameters::class.java))
    }
  }
}

