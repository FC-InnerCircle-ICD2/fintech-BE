package com.inner.circle.api.config

import com.github.dockerjava.api.command.CreateContainerCmd
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class RedisTestContainerConfiguration :
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger: Logger =
        LoggerFactory.getLogger(
            RedisTestContainerConfiguration::class.java
        )

    companion object {
        private const val IMAGE_TAG = "redis:6.2.6"
        private const val PAYMENT_REDIS_PORT = 6379
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val env = applicationContext.environment
        val containerName = "reusable-redis"

        val dockerImageName = DockerImageName.parse(IMAGE_TAG).asCompatibleSubstituteFor("redis")
        val container =
            GenericContainer<Nothing>(dockerImageName).apply {
                if (env.activeProfiles.contains("local")) {
                    withReuse(true)
                }
                withCreateContainerCmdModifier(fun(cmd: CreateContainerCmd) {
                    cmd.withName(containerName)
                })
                withExposedPorts(PAYMENT_REDIS_PORT)
            }

        try {
            container.start()
            logger.info("Redis is up and running")
            logger.info("Redis container logs: \n{}", container.logs)
        } catch (e: Exception) {
            logger.error("Failed to start Redis container", e)
            throw e
        }

        val host = container.host
        val port = container.getMappedPort(PAYMENT_REDIS_PORT).toString()
        System.setProperty("spring.data.redis.host", host)
        System.setProperty("spring.data.redis.port", port)
        logger.info("Redis host: {}", host)
        logger.info("Redis port: {}", port)
    }
}
