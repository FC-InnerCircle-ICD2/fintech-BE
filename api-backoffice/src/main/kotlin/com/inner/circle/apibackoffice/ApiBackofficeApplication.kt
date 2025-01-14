package com.inner.circle.apibackoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.inner.circle.apibackoffice",
        "com.inner.circle.corebackoffice",
        "com.inner.circle.infrabackoffice"
    ]
)
class ApiBackofficeApplication

fun main(args: Array<String>) {
    runApplication<ApiBackofficeApplication>(*args)
}
