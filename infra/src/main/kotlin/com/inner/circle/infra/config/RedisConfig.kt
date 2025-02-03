package com.inner.circle.infra.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
class RedisConfig {
    lateinit var host: String
    var port: Int = 6379

    // https://redis.github.io/lettuce/#ssl
    @Bean
    fun redisConnectionFactory(environment: Environment): RedisConnectionFactory {
        val redisConfiguration = RedisStandaloneConfiguration(host, port)
        val lettuceClientConfigurationBuilder =
            LettuceClientConfiguration
                .builder()

        if (!environment.matchesProfiles("local", "test")) {
            lettuceClientConfigurationBuilder.useSsl().disablePeerVerification()
        }

        val lettuceClientConfiguration =
            lettuceClientConfigurationBuilder
                .build()

        return LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        val template = StringRedisTemplate(redisConnectionFactory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }
}
