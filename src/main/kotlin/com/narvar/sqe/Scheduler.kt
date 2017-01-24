package com.narvar.sqe

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
open class Scheduler {

  @Scheduled(fixedRate = 300000)
  open fun pingHealthCheck() {
    val response =
        RestTemplateBuilder()
            .build()
            .getForObject("https://stark-brushlands-69348.herokuapp.com/health_check", String::class.java)
    println(response)
  }
}