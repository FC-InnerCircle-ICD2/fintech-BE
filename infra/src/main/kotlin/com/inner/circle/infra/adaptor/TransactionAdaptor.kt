package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.TransactionPort
import com.inner.circle.infra.repository.TransactionRepository
import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository
) : TransactionPort {
    override fun save(
        request: TransactionPort.Request
    ): TransactionEntity {
        return transactionRepository.save(
            TransactionEntity(
                paymentKey = request.paymentKey,
                payment = request.payment,
                amount = request.amount,
                status = request.status,
                reason = request.reason,
                requestTime = null,
                completionTime = null
            )
        )
    }
}
