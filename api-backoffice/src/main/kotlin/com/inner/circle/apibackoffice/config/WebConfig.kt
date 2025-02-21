package com.inner.circle.apibackoffice.config

import com.inner.circle.apibackoffice.interceptor.AuthCheckInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private var authCheckInterceptor: AuthCheckInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authCheckInterceptor)
            .addPathPatterns("/api/backoffice/v1")
            .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
        // .allowCredentials(true)
    }
}
