package com.inner.circle.api

import com.inner.circle.api.config.PostgreSqlTestContainerConfiguration
import com.inner.circle.api.config.RedisTestContainerConfiguration
import org.springframework.boot.runApplication

object TestApiPaymentApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<ApiApplication>(*args) {
            addInitializers(PostgreSqlTestContainerConfiguration())
            addInitializers(RedisTestContainerConfiguration())
        }
    }
}
