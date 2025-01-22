package com.inner.circle.infra.adaptor

import com.inner.circle.infra.adaptor.dto.CardPaymentAuthInfraDto
import com.inner.circle.infra.externalapi.CardAuthClient
import com.inner.circle.infra.port.CardPaymentAuthPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
internal class CardPaymentAuthAdaptor(
    private val cardAuthClient: CardAuthClient
) : CardPaymentAuthPort {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun doPaymentAuth(request: CardPaymentAuthPort.Request): CardPaymentAuthInfraDto =
        runCatching {
            val defaultCardAuthResponse =
                CardPaymentAuthInfraDto(
                    cardNumber = request.cardNumber,
                    isValid = false
                )

            cardAuthClient
                .validateCardPayment(
                    request = defaultCardAuthResponse
                ).execute()
                .takeIf { it.isSuccessful && (it.body() != null) }
                ?.let {
                    defaultCardAuthResponse.copy(
                        isValid = true
                    )
                } ?: defaultCardAuthResponse
        }.onFailure {
            logger.error("Payment Auth Request 중 에러가 발생 ${it.message}")
        }.getOrThrow()
}
