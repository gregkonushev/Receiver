package com.narvar.sqe.config

import com.narvar.sqe.config.RedisCredentials.hostName
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.clients.jedis.JedisPoolConfig

@Configuration
@EnableRedisRepositories
open class RedisConfiguration {

  @Bean
  open fun buildConnection(): RedisConnectionFactory =
      JedisConnectionFactory(
          JedisPoolConfig()
              .apply {
                maxTotal = 10
                maxIdle = 5
                minIdle = 1
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
              }
      ).apply {
        hostName = RedisCredentials.hostName
        port = RedisCredentials.port
        password = RedisCredentials.password
      }

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