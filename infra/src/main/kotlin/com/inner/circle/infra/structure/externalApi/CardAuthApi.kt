package com.inner.circle.infra.structure.externalApi

import com.inner.circle.infra.structure.adaptor.dto.CardPaymentAuthInfraDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CardAuthApi {
    @POST("mock/card/validate")
    fun validateCardPayment(@Body request: CardPaymentAuthInfraDto): Call<CardPaymentAuthInfraDto>
}
