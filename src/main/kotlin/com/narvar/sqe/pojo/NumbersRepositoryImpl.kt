package com.narvar.sqe.pojo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository

@Repository
open class NumbersRepositoryImpl
@Autowired
constructor(var redisTemplate: RedisTemplate<String, Any>, var mapper: ObjectMapper) : NumbersRepository {

  private var valueOps: ValueOperations<String, Any> = redisTemplate.opsForValue()

  override fun saveNumbers(numbers: TrackingNumbers) {
    with(numbers) {
      valueOps.set(key, mapper.writeValueAsString(numbers.values))
    }
  }

  override fun getNumbersByCarrierAndStatus(parameters: QueryParameters): TrackingNumbers {
    with(parameters) {
      return TrackingNumbers(carrier, status, partner_carrier,
          mapper.readValue<List<String>>(valueOps.get(key).toString())
      )
    }
  }

  override fun getAllNumbers(): TrackingNumbers {
    println(redisTemplate.keys("*"))
    return TrackingNumbers("fedex", "in-transit", null, listOf("123123213"))
  }

}
