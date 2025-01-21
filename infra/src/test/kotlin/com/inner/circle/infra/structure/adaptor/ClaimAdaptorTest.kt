package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.AbstractJpaTestWithLocalTestContainer
import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.structure.adaptor.enum.PaymentProcessStatus
import com.inner.circle.infra.structure.repository.JpaConfiguration
import com.inner.circle.infra.structure.repository.PaymentClaimRepositoryHandler
import java.math.BigDecimal
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration


@ContextConfiguration(classes = [PaymentClaimRepositoryHandler::class, JpaConfiguration::class])
class ClaimAdaptorTest : AbstractJpaTestWithLocalTestContainer() {
    @Autowired
    private lateinit var repository: PaymentClaimRepositoryHandler

    @Test
    fun paymentRequestSaveTest() {
        // Given
        val orderId = "12345"
        val paymentClaimDto =
            PaymentClaimDto(
                paymentRequestId = null,
                orderId = orderId,
                orderName = "Test Order",
                orderStatus = PaymentProcessStatus.READY,
                merchantId = "merchant123",
                paymentKey = null,
                amount = BigDecimal(100.00),
                requestTime = LocalDateTime.now(),
                successUrl = "https://www.test.com/success",
                failUrl = "https://www.test.com/fail",
                paymentToken = null
            )
        val paymentRequestEntity = paymentClaimDto.toInitGenerate()

        // When
        val requestEntity = repository.save(paymentRequestEntity)
        val savedEntity = repository.findByOrderId(paymentRequestEntity.orderId)

        // Then
        assertEquals(paymentClaimDto.orderId, savedEntity?.orderId)
        assertEquals(paymentClaimDto.orderName, savedEntity?.orderName)
        assertEquals(
            paymentClaimDto.orderStatus.name,
            savedEntity?.orderStatus
        )
        assertEquals(paymentClaimDto.merchantId, savedEntity?.merchantId)
        assertEquals(paymentClaimDto.paymentKey, savedEntity?.paymentKey)
        assertEquals(paymentClaimDto.amount, savedEntity?.amount)
        assertEquals(paymentClaimDto.requestTime, savedEntity?.requestTime)
    }
}