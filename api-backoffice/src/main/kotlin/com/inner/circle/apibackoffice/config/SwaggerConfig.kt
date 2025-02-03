package com.inner.circle.apibackoffice.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
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
    val securitySchemeName = "basicAuth"

    @Bean
    fun customOpenAPI(): OpenAPI {
        val server = Server().url(serverUrl) // 환경별 서버 URL 설정

        return OpenAPI()
            .info(
                Info()
                    .title("Pay200 API Docs")
                    .version("1.0")
                    .description("PG 백오피스 프로젝트")
            ).addServersItem(server)
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                io.swagger.v3.oas.models
                    .Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("basic")
                    )
            )
    }
}
