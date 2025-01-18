package com.inner.circle.infra.structure.externalapi

import com.inner.circle.infra.structure.adaptor.dto.CardPaymentAuthInfraDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CardAuthClient {
    @POST("mock/card/validate")
    fun validateCardPayment(
        @Body request: CardPaymentAuthInfraDto
    ): Call<CardPaymentAuthInfraDto>
}
