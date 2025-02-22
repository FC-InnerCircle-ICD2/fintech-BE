package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.PaymentException.PaymentNotFoundException
import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto
import com.inner.circle.infrabackoffice.port.GetPaymentPort
import com.inner.circle.infrabackoffice.repository.PaymentRepository
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : GetPaymentPort {
    override fun findAllByMerchantId(
        request: GetPaymentPort.FindAllByMerchantIdRequest
    ): List<PaymentDto> =
        paymentRepository
            .findAllByMerchantId(
                merchantId = request.merchantId,
                paymentKey = request.paymentKey,
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
                ?: throw PaymentNotFoundException(
                    paymentId = "",
                    message =
                        "요청된 결제 정보를 찾을 수 없습니다. : " +
                            "가맹점[${request.merchantId}], paymentKey[${request.paymentKey}]"
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
