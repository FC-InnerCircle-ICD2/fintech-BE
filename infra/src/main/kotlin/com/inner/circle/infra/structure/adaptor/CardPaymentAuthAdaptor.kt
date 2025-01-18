package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.adaptor.dto.CardPaymentAuthInfraDto
import com.inner.circle.infra.structure.externalApi.CardAuthApi
import com.inner.circle.infra.structure.port.CardPaymentAuthPort
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
internal class CardPaymentAuthAdaptor : CardPaymentAuthPort {
    // Retrofit 인스턴스 생성
    private val retrofit: Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://localhost:8082/") // API의 기본 URL
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 사용
            .build()

    // API 서비스 인스턴스 생성
    private val apiService: CardAuthApi = retrofit.create(CardAuthApi::class.java)

    override fun doPaymentAuth(request: CardPaymentAuthPort.Request): CardPaymentAuthInfraDto {
        val requestDto =
            CardPaymentAuthInfraDto(
                cardNumber = request.cardNumber,
                isValid = false
            )

        // API 호출
        val call: Call<CardPaymentAuthInfraDto> = apiService.validateCardPayment(requestDto)
        val response: Response<CardPaymentAuthInfraDto> = call.execute()

        // 응답 처리
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                CardPaymentAuthInfraDto(
                    cardNumber = request.cardNumber,
                    isValid = responseBody.isValid
                )
            } else {
                // 응답이 null인 경우
                CardPaymentAuthInfraDto(
                    cardNumber = request.cardNumber,
                    isValid = false
                )
            }
        } else {
            // 실패한 경우
            CardPaymentAuthInfraDto(
                cardNumber = request.cardNumber,
                isValid = false
            )
        }
    }
}
