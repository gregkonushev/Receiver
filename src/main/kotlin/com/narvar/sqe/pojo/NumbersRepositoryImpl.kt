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

  override fun getNumbersByCarrierAndStatus(parameters: QueryParameters): Numbers {
    with(parameters) {
      return Numbers(tracking_numbers = mutableListOf(TrackingNumbers(carrier, status, partner_carrier, getListFromKey(key))))
    }
  }

  override fun getAllNumbers(): Numbers {
    val numbers = Numbers(tracking_numbers = mutableListOf())
    redisTemplate.keys("*").forEach {
      numbers.tracking_numbers.add(
          TrackingNumbers(status = it.getStatus(),
              carrier = it.getCarrier(),
              partner_carrier = it.getPartnerCarrier(),
              values = getListFromKey(it)
          )
      )
    }
    return numbers
  }

  private fun getListFromKey(key: String) =
      mapper.readValue<MutableList<String>>(valueOps.get(key).toString())

}
