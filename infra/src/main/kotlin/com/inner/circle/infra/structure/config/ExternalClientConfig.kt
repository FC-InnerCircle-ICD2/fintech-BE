package com.inner.circle.infra.structure.config

import com.inner.circle.infra.structure.externalapi.CardAuthClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class ExternalClientConfig {

    @Bean
    fun mockServerClient(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://localhost:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Bean
    fun cardAuthClient(mockServerClient: Retrofit): CardAuthClient =
        mockServerClient.create(CardAuthClient::class.java)
}
