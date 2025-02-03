package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.TransactionPort
import com.inner.circle.infra.repository.PaymentRepository
import com.inner.circle.infra.repository.TransactionRepository
import com.inner.circle.infra.repository.entity.PaymentEntity
import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository,
    private val paymentRepository: PaymentRepository
) : TransactionPort {
    override fun save(request: TransactionPort.Request): TransactionEntity {
        val paymentEntity: PaymentEntity =
            paymentRepository.findByPaymentKey(
                request.paymentKey
            ) ?: throw IllegalArgumentException(
                "Payment not found - payment_key: ${request.paymentKey}"
            )

        return transactionRepository.save(
            TransactionEntity(
                paymentKey = request.paymentKey,
                payment = paymentEntity,
                amount = request.amount,
                status = request.status,
                reason = request.reason,
                requestTime = null,
                completionTime = null
            )
        ) ?: throw IllegalArgumentException(
            "Payment Transaction not save"
        )
    }
}
