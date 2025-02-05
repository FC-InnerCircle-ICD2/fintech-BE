package com.inner.circle.infra.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class TestRedisConfiguration {
    private val logger: Logger = LoggerFactory.getLogger(TestRedisConfiguration::class.java)

    companion object {
        private const val IMAGE_TAG = "redis:6.2.6"
        private const val REDIS_PORT = 6379
        val container: GenericContainer<Nothing> by lazy {
            val dockerImageName =
                DockerImageName
                    .parse(
                        IMAGE_TAG
                    ).asCompatibleSubstituteFor("redis")
            GenericContainer<Nothing>(dockerImageName).apply {
                withExposedPorts(REDIS_PORT)
                start()
            }
        }
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val host = container.host
        val port = container.getMappedPort(REDIS_PORT)
        logger.info("Redis host: {}", host)
        logger.info("Redis port: {}", port)
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory): StringRedisTemplate =
        StringRedisTemplate(redisConnectionFactory)
}
