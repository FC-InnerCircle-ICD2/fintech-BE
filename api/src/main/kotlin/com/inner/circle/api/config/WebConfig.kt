package com.inner.circle.api.config

import com.inner.circle.api.interceptor.AuthCheckInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private var authCheckInterceptor: AuthCheckInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authCheckInterceptor)
            .addPathPatterns("/api/v1/p/user") // payment - user에서만 우선 동작하도록 수정
            .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**")
    }
}
