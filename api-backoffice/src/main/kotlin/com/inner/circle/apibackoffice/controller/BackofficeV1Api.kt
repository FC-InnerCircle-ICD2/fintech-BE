package com.inner.circle.apibackoffice.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
@RequestMapping("/api/v1/backoffice")
annotation class BackofficeV1Api
