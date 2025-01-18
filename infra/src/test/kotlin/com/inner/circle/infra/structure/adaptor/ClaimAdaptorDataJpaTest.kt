package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.AbstractJpaTestWithLocalTestContainer
import com.inner.circle.infra.structure.adaptor.dto.PaymentRequestDto
import com.inner.circle.infra.structure.repository.JpaConfiguration
import com.inner.circle.infra.structure.repository.PaymentClaimJpaRepository
import com.inner.circle.infra.structure.repository.PaymentClaimJpaRepositoryAdapter
import java.math.BigDecimal
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [PaymentClaimJpaRepositoryAdapter::class, JpaConfiguration::class])
class ClaimAdaptorDataJpaTest : AbstractJpaTestWithLocalTestContainer() {
    @Autowired
    private lateinit var repository: PaymentClaimJpaRepository

    @Test
    fun paymentRequestSaveTest() {
        // Given
        val orderId = "12345"
        val paymentRequestDto =
            PaymentRequestDto(
                orderId = orderId,
                orderName = "Test Order",
                orderStatus = PaymentProcessStatus.READY,
                merchantId = "merchant123",
                paymentKey = null,
                amount = BigDecimal(100.00),
                requestTime = LocalDateTime.now(),
                successUrl = "https://www.test.com/success",
                failUrl = "https://www.test.com/fail"
            )
        val paymentRequestEntity = paymentRequestDto.toEntity()

        // When
        val requestEntity = repository.save(paymentRequestEntity)
        val savedEntity = repository.findByIdOrNull(paymentRequestEntity.orderId)

        // Then
        assertEquals(paymentRequestDto.orderId, savedEntity?.orderId)
        assertEquals(paymentRequestDto.orderName, savedEntity?.orderName)
        assertEquals(
            paymentRequestDto.orderStatus,
            savedEntity?.orderStatus?.let {
                PaymentProcessStatus.valueOf(it)
            }
        )
        assertEquals(paymentRequestDto.merchantId, savedEntity?.merchantId)
        assertEquals(paymentRequestDto.paymentKey, savedEntity?.paymentKey)
        assertEquals(paymentRequestDto.amount, savedEntity?.amount)
        assertEquals(paymentRequestDto.requestTime, savedEntity?.requestTime)
    }
}
