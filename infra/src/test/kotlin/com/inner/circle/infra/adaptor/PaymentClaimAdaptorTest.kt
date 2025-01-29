package com.inner.circle.infra.adaptor

import AbstractJpaTestWithLocalTestContainer
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.repository.entity.PaymentClaimRepositoryHandler
import java.math.BigDecimal
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

@ContextConfiguration(classes = [PaymentClaimRepositoryHandler::class])
class PaymentClaimAdaptorTest : AbstractJpaTestWithLocalTestContainer() {
    @Autowired
    private lateinit var repository: PaymentClaimRepositoryHandler

    @Test
    @Transactional
    fun paymentRequestSaveTest() {
        // Given
        val orderId = "12345"
        val merchantId = "merchant123"
        val paymentClaimDto =
            PaymentClaimDto(
                paymentRequestId = null,
                orderId = orderId,
                orderName = "Test Order",
                orderStatus = PaymentProcessStatus.READY,
                merchantId = merchantId,
                paymentType = null,
                cardNumber = null,
                paymentKey = null,
                amount = BigDecimal(100.00),
                requestTime = LocalDateTime.now(),
                paymentToken = null
            )

        val jwtToken = generateJwtToken(merchantId, orderId)

        val tokenData =
            PaymentTokenDto(
                merchantId = merchantId,
                orderId = orderId,
                generatedToken = jwtToken,
                expiresAt = LocalDateTime.now().plusMinutes(3)
            )

        val paymentRequestEntity = paymentClaimDto.toInitGenerate(tokenData)

        // When
        repository.save(paymentRequestEntity)
        val savedEntity = repository.findByOrderId(paymentRequestEntity.orderId)

        // Then
        assertThat(savedEntity).isNotNull
        assertThat(savedEntity?.orderId).isEqualTo(paymentClaimDto.orderId)
        assertThat(savedEntity?.orderName).isEqualTo(paymentClaimDto.orderName)
        assertThat(savedEntity?.orderStatus).isEqualTo(paymentClaimDto.orderStatus.name)
        assertThat(savedEntity?.merchantId).isEqualTo(paymentClaimDto.merchantId)
        assertThat(savedEntity?.paymentKey).isEqualTo(paymentClaimDto.paymentKey)
        assertThat(savedEntity?.amount).isEqualByComparingTo(paymentClaimDto.amount)
        assertThat(savedEntity?.requestTime).isEqualTo(paymentClaimDto.requestTime)
    }

    private fun generateJwtToken(
        merchantId: String,
        orderId: String
    ): String = "test_jwt_token"
}
