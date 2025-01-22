package com.inner.circle.infra.config

import com.inner.circle.infra.externalapi.CardAuthClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class ExternalClientConfig(
    @Value("\${external.mock-server.base-url}")
    private val mockServerBaseUrl: String
) {
    @Bean
    fun mockServerClient(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(mockServerBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Bean
    fun cardAuthClient(mockServerClient: Retrofit): CardAuthClient =
        mockServerClient.create(CardAuthClient::class.java)
}
