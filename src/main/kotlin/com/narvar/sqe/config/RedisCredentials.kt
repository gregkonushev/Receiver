package com.narvar.sqe.config

object RedisCredentials {
  val env = System.getenv("REDIS_URL")
  val hostName = env.substringAfter("@").substringBefore(":")
  val port = env.substringAfterLast(":").toInt()
  val password = env.substringAfter("h:").substringBefore("@")
}