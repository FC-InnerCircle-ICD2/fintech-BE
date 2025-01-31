package com.inner.circle.infra.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

@Profile("!local || !test")
@Configuration
class EmbeddedRedisConfig {
    private val logger: Logger = LoggerFactory.getLogger(EmbeddedRedisConfig::class.java)

    @Value("\${spring.data.redis.port}")
    private val redisPort: Int = 6379
    private var redisServer: RedisServer? = null

    @PostConstruct
    fun startRedisServer() {
        logger.info("Starting embedded redis. (redisPort: $redisPort)")
        redisServer = RedisServer(redisPort)
        redisServer?.start()
    }

    @PreDestroy
    fun stopRedis() {
        logger.info("Stopping embedded redis")
        redisServer?.stop()
    }
}
