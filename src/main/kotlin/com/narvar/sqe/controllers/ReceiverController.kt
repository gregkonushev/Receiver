package com.narvar.sqe.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.narvar.sqe.pojo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
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
  fun receiveNumbers(@RequestBody body: StandardResponse): StandardResponse {
    body.tracking_numbers?.forEach {
      repo.saveNumbers(it)
    }
    return StandardResponse(200, "values saved in redis", body.tracking_numbers)
  }

  @ResponseStatus(OK)
  @RequestMapping("/tracking_numbers", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json"))
  fun getNumbers(@RequestParam queryParameters: Map<String, String>?) : ResponseEntity<Any> {
    try {
      if (queryParameters?.isEmpty() as Boolean) {
        return ResponseEntity(StandardResponse(
            status_code = 200,
            message = "OK",
            tracking_numbers = repo.getAllNumbers()),
            OK)
      } else {
        return ResponseEntity(StandardResponse(
            status_code = 200,
            message = "OK",
            tracking_numbers = repo.getNumbersByParameters(mapper.convertValue(queryParameters, TrackingNumbers::class.java))),
            OK)
      }
    } catch (e: Exception) {
      return ResponseEntity(StandardResponse(404, "Information was not found", null), NOT_FOUND)
    }
  }
}

