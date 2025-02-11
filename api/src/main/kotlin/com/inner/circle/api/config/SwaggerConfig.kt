package com.inner.circle.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("local | dev")
@Configuration
class SwaggerConfig(
    @Value("\${swagger.server-url}") private val serverUrl: String
) {
    private val basicSchemeName = "basic"
    private val bearerSchemaName = "bearer"

    @Bean
    fun customOpenAPI(): OpenAPI {
        val basicAuthScheme =
            SecurityScheme()
                .name(basicSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme(basicSchemeName)

        val bearerAuthScheme =
            SecurityScheme()
                .name(bearerSchemaName)
                .type(SecurityScheme.Type.HTTP)
                .scheme(bearerSchemaName)
                .bearerFormat("JWT")

        return OpenAPI()
            .info(
                Info()
                    .title("API Documentation")
                    .version("v1")
            ).components(
                Components()
                    .addSecuritySchemes("basicAuth", basicAuthScheme)
                    .addSecuritySchemes("bearerAuth", bearerAuthScheme)
            ).addSecurityItem(SecurityRequirement().addList(basicSchemeName))
            .addSecurityItem(SecurityRequirement().addList(bearerSchemaName))
    }
}
