package com.inner.circle.api.config

import io.micrometer.common.util.StringUtils
import jakarta.annotation.PreDestroy
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import redis.embedded.RedisServer

@TestConfiguration
class EmbeddedRedisConfiguration : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val logger: Logger = LoggerFactory.getLogger(EmbeddedRedisConfiguration::class.java)

    @Value("\${spring.data.redis.port}")
    private val redisPort: Int = 6379
    private lateinit var redisServer: RedisServer

    @Throws(IOException::class)
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        try {
            val selectedRedisPort = if (isRedisRunning()) findAvailablePort() else redisPort
            redisServer =
                RedisServer
                    .newRedisServer()
                    .port(selectedRedisPort)
                    .setting("maxmemory 128M")
                    .build()
            redisServer.start()
            logger.info("Embedded Redis server started on port $selectedRedisPort")
        } catch (e: IOException) {
            throw RuntimeException("Failed to start embedded Redis server", e)
        }
    }

    @PreDestroy
    fun stopRedis() {
        if (this::redisServer.isInitialized) {
            redisServer.stop()
        }
    }

    @Throws(IOException::class)
    private fun isRedisRunning(): Boolean = isRunning(executeGrepProcessCommand(redisPort))

    @Throws(IOException::class)
    fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000 ~ 65535")
    }

    @Throws(IOException::class)
    private fun executeGrepProcessCommand(port: Int): Process {
        val systemOsName = System.getProperty("os.name").lowercase(Locale.getDefault())
        val command =
            if (systemOsName.contains("win")) {
                "netstat -nao | find \"LISTEN\" | find \"$port\""
            } else {
                "netstat -nat | grep LISTEN | grep $port"
            }
        val shell =
            if (systemOsName.contains("win")) {
                arrayOf("cmd.exe", "/y", "/c", command)
            } else {
                arrayOf("/bin/sh", "-c", command)
            }
        return Runtime.getRuntime().exec(shell)
    }

    private fun isRunning(process: Process): Boolean {
        val pidInfo = StringBuilder()
        try {
            BufferedReader(InputStreamReader(process.inputStream)).use { input ->
                input.lines().forEach { pidInfo.append(it) }
            }
        } catch (e: Exception) {
            // Log or handle the exception if necessary
        }
        return StringUtils.isNotEmpty(pidInfo.toString())
    }

    private fun isMProcessorMac(): Boolean =
        System.getProperty("os.arch") == "aarch64" &&
                System.getProperty("os.name") == "Mac OS X"
}
