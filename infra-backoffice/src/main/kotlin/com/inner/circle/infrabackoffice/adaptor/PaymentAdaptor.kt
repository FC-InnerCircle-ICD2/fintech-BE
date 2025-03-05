package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.PaymentException.PaymentNotFoundException
import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto
import com.inner.circle.infrabackoffice.port.GetPaymentPort
import com.inner.circle.infrabackoffice.repository.PaymentRepository
import kotlinx.datetime.toJavaLocalDate
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : GetPaymentPort {
    override fun findAllByMerchantId(
        request: GetPaymentPort.FindAllByMerchantIdRequest
    ): List<PaymentDto> =
        paymentRepository
            .findAllByMerchantIdOrderByCreatedAtDesc(
                merchantId = request.merchantId,
                paymentKey = request.paymentKey,
                startDate = request.startDate?.toJavaLocalDate(),
                endDate = request.endDate?.toJavaLocalDate(),
                page = request.page,
                limit = request.limit
            ).map { payment -> PaymentDto.of(payment) }

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
        return PaymentDto.of(payment)
    }
}
