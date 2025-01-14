package com.inner.circle.api

import com.inner.circle.api.config.PostgreSqlTestContainerConfiguration
import org.springframework.boot.runApplication


object TestApiPaymentApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<ApiApplication>(*args) {
            addInitializers(PostgreSqlTestContainerConfiguration())
        }
    }
}