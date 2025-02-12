package com.inner.circle.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("local | dev")
@Configuration
class SwaggerConfig(
    @Value("\${swagger.server-url}") private val serverUrl: String
) {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val basicAuthScheme =
            SecurityScheme()
                .name(BASIC_AUTH)
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic")

        val bearerAuthScheme =
            SecurityScheme()
                .name(BEARER_AUTH)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")

        return OpenAPI()
            .info(
                Info()
                    .title("API Documentation")
                    .version("v1")
            ).servers(
                listOf(
                    Server().url(serverUrl).description("HTTPS Server")
                )
            ).components(
                Components()
                    .addSecuritySchemes(BASIC_AUTH, basicAuthScheme)
                    .addSecuritySchemes(BEARER_AUTH, bearerAuthScheme)
            )
    }

    companion object {
        const val BASIC_AUTH = "basicAuth"
        const val BEARER_AUTH = "bearerAuth"
    }
}
