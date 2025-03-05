package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.PaymentDto
import com.inner.circle.infra.port.GetPaymentPort
import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.repository.PaymentRepository
import com.inner.circle.infra.repository.entity.PaymentEntity
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : PaymentPort,
    GetPaymentPort {
    override fun save(request: PaymentPort.Request) {
        paymentRepository.save(
            PaymentEntity(
                id = request.id,
                paymentKey = request.paymentKey,
                cardNumber = request.cardNumber,
                currency = request.currency,
                accountId = request.accountId,
                merchantId = request.merchantId,
                paymentType = request.paymentType,
                orderId = request.orderId,
                orderName = request.orderName
            )
        ) ?: throw PaymentException.PaymentNotSaveException(
            paymentKey = request.paymentKey
        )
    }

    override fun findAllByAccountId(
        request: GetPaymentPort.FindAllByAccountIdRequest
    ): List<PaymentDto> =
        paymentRepository
            .findAllByAccountIdOrderByCreatedAtDesc(
                accountId = request.accountId,
                startDate = request.startDate?.toJavaLocalDate(),
                endDate = request.endDate?.toJavaLocalDate(),
                page = request.page,
                limit = request.limit
            ).map { payment ->
                PaymentDto(
                    id = requireNotNull(payment.id),
                    paymentKey = payment.paymentKey,
                    cardNumber = payment.cardNumber,
                    currency = payment.currency,
                    accountId = requireNotNull(payment.accountId),
                    merchantId = payment.merchantId,
                    paymentType = payment.paymentType,
                    orderId = payment.orderId,
                    orderName = payment.orderName,
                    createdAt = payment.createdAt.toKotlinLocalDateTime(),
                    updatedAt = payment.updatedAt.toKotlinLocalDateTime()
                )
            }

    override fun findByMerchantIdAndPaymentKey(
        request: GetPaymentPort.FindByPaymentKeyRequest
    ): PaymentDto {
        val payment =
            paymentRepository.findByMerchantIdAndPaymentKey(
                merchantId = request.merchantId,
                paymentKey = request.paymentKey
            )
                ?: throw PaymentException.PaymentNotFoundException(
                    paymentId = "",
                    message = "요청된 결제 정보를 찾을 수 없습니다. : paymentKey[${request.paymentKey}]"
                )
        return PaymentDto(
            id = requireNotNull(payment.id),
            paymentKey = payment.paymentKey,
            cardNumber = payment.cardNumber,
            currency = payment.currency,
            accountId = requireNotNull(payment.accountId),
            merchantId = payment.merchantId,
            paymentType = payment.paymentType,
            orderId = payment.orderId,
            orderName = payment.orderName,
            createdAt = payment.createdAt.toKotlinLocalDateTime(),
            updatedAt = payment.updatedAt.toKotlinLocalDateTime()
        )
    }
}
