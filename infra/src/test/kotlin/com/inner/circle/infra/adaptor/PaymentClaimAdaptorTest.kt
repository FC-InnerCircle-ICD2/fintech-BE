package com.inner.circle.infra.adaptor

import AbstractJpaTestWithLocalTestContainer
import com.inner.circle.exception.PaymentClaimException
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.repository.PaymentClaimJpaRepository
import com.inner.circle.infra.repository.entity.PaymentClaimRepository
import com.inner.circle.infra.repository.entity.PaymentClaimRepositoryHandler
import com.inner.circle.infra.repository.entity.PaymentStatusType
import java.math.BigDecimal
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

@ContextConfiguration(
    classes = [PaymentClaimJpaRepository::class, PaymentClaimRepositoryHandler::class]
)
class PaymentClaimAdaptorTest : AbstractJpaTestWithLocalTestContainer() {
    @Autowired
    private lateinit var repository: PaymentClaimRepository

    @DisplayName("PaymentRequest 저장 동작 테스트")
    @Test
    @Transactional
    fun paymentRequestSaveTest() {
        // Given
        val orderId = "12345"
        val merchantId = 123L
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
                expiredAt = LocalDateTime.now().plusMinutes(3),
                signature = ""
            )

        val paymentRequestEntity = paymentClaimDto.toInitGenerate(tokenData)

        // When
        repository.save(paymentRequestEntity)
        val savedEntity = repository.findByOrderId(paymentRequestEntity.orderId)

        // Then
        assertThat(savedEntity).isNotNull
        assertThat(savedEntity?.orderId).isEqualTo(paymentClaimDto.orderId)
        assertThat(savedEntity?.orderName).isEqualTo(paymentClaimDto.orderName)
        assertThat(
            savedEntity?.orderStatus?.name ?: PaymentStatusType.READY.name
        ).isEqualTo(paymentClaimDto.orderStatus.name)
        assertThat(savedEntity?.merchantId).isEqualTo(paymentClaimDto.merchantId)
        assertThat(savedEntity?.paymentKey).isEqualTo(paymentClaimDto.paymentKey)
        assertThat(savedEntity?.amount).isEqualByComparingTo(paymentClaimDto.amount)
        assertThat(savedEntity?.requestTime).isEqualTo(paymentClaimDto.requestTime)
    }

    @DisplayName("PaymentClaimDto 구성 시 READY 상태에서 음수 저장 시도 시 예외를 반환한다.")
    @Test
    @Transactional
    fun fail_paymentRequestSaveFailureWhenOrderStatusIsReadyAndMinusAmountTest() {
        // Given
        val orderId = "12345"
        val merchantId = 123L

        // When & Then
        val exception =
            assertThrows(PaymentClaimException.InvalidClaimAmountException::class.java) {
                PaymentClaimDto(
                    paymentRequestId = null,
                    orderId = orderId,
                    orderName = "Test Order",
                    orderStatus = PaymentProcessStatus.READY,
                    merchantId = merchantId,
                    paymentType = null,
                    cardNumber = null,
                    paymentKey = null,
                    amount = BigDecimal(-100.00),
                    requestTime = LocalDateTime.now(),
                    paymentToken = null
                )
            }
        assertThat(
            exception.message
        ).isEqualTo("주문 ID (12345)와 관련된 요청 금액이 유효하지 않습니다.")
    }

    @DisplayName("PaymentClaimDto 구성 시 PaymentProcessStatus.READY가 아니면 예외를 반환한다.")
    @Test
    @Transactional
    fun fail_paymentRequestSaveFailureDueToNullStatusTest() {
        // Given
        val orderId = "12345"
        val merchantId = 123L

        // When & Then
        assertThrows(PaymentClaimException.BadPaymentClaimRequestException::class.java) {
            PaymentClaimDto(
                paymentRequestId = null,
                orderId = orderId,
                orderName = "Test Order",
                orderStatus = PaymentProcessStatus.DONE,
                merchantId = merchantId,
                paymentType = null,
                cardNumber = null,
                paymentKey = null,
                amount = BigDecimal(100.00),
                requestTime = LocalDateTime.now(),
                paymentToken = null
            )
        }
    }

    @DisplayName("PaymentClaimDto 생성 시 필수로 확인되어야 하는 정보가 없으면 예외를 반환한다.")
    @Test
    @Transactional
    fun fail_paymentRequestSaveFailureDueToMissingRequiredInformationTest() {
        // Given
        val orderId = "12345"
        val merchantId = 123L

        // When & Then
        assertThrows(PaymentClaimException.BadPaymentClaimRequestException::class.java) {
            PaymentClaimDto(
                paymentRequestId = null,
                orderId = orderId,
                orderName = null,
                orderStatus = PaymentProcessStatus.READY,
                merchantId = merchantId,
                paymentType = null,
                cardNumber = null,
                paymentKey = null,
                amount = BigDecimal(100.00),
                requestTime = LocalDateTime.now(),
                paymentToken = null
            )
        }
    }

    @DisplayName("동일 orderId를 저장하려 시도하면 예외를 반환한다.")
    @Test
    @Transactional
    fun fail_duplicatePaymentRequestSaveTest() {
        // Given
        val orderId = "12345"
        val merchantId = 123L
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
                expiredAt = LocalDateTime.now().plusMinutes(3),
                signature = ""
            )

        val paymentRequestEntity = paymentClaimDto.toInitGenerate(tokenData)

        // When
        repository.save(paymentRequestEntity)
        val duplicatePaymentRequestEntity = paymentClaimDto.toInitGenerate(tokenData)

        // Then
        assertThrows(PaymentClaimException.ClaimAlreadyExistsException::class.java) {
            repository.save(duplicatePaymentRequestEntity)
        }
    }

    private fun generateJwtToken(
        merchantId: Long,
        orderId: String
    ): String = "test_jwt_token"
}
