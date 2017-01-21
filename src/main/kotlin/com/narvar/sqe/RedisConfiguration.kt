package com.narvar.sqe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
open class RedisConfiguration {

  @Bean
  open fun buildConnection(): RedisConnectionFactory =
      JedisConnectionFactory(
          /*JedisPoolConfig()
              .apply {
                maxTotal = 10
                maxIdle = 5
                minIdle = 1
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
              }*/
      )

  @Bean
  open fun redisTemplate(): RedisTemplate<String, Any> {
    with(RedisTemplate<String, Any>()) {
      connectionFactory = buildConnection()
      keySerializer = StringRedisSerializer()
      hashValueSerializer = GenericToStringSerializer(Any::class.java)
      valueSerializer = GenericToStringSerializer(Any::class.java)
      return this
    }
  }

}