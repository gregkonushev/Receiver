package com.narvar.sqe.controllers

import com.narvar.sqe.pojo.Numbers
import com.narvar.sqe.pojo.NumbersRepository
import com.narvar.sqe.pojo.StandardResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@RestController
open class ReceiverController {

  @Autowired
  lateinit var repo: NumbersRepository

  @ResponseStatus(OK)
  @RequestMapping("/")
  fun rootDirectory() = "Welcome to Receiver App"

  @ResponseStatus(OK)
  @RequestMapping("/health_check", method = arrayOf(RequestMethod.GET))
  fun healthCheck(): String = System.getenv("REDIS_URL")

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
  fun getNumbers() = repo.getAllNumbers()

}

