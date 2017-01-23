package com.narvar.sqe.pojo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
open class NumbersRepositoryImpl
@Autowired
constructor(private var redisTemplate: RedisTemplate<String, Any>) : NumbersRepository {

  private lateinit var valueOps: ValueOperations<String, Any>

  @PostConstruct
  private fun initialize() {
    valueOps = redisTemplate.opsForValue()
  }

  override fun saveNumbers(numbers: TrackingNumbers) {
    with(numbers) {
      valueOps.set("$carrier_name:$status", jacksonObjectMapper().writeValueAsString(numbers.values))
    }
  }

  override fun getAllNumbers() {

  }

}
