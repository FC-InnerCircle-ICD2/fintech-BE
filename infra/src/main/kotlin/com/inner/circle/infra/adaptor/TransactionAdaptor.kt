package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.TransactionPort
import com.inner.circle.infra.repository.TransactionRepository
import com.inner.circle.infra.repository.entity.TransactionEntity
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository
) : TransactionPort {
    @Transactional
    override fun save(request: TransactionPort.Request) {
        transactionRepository.save(
            TransactionEntity(
                id = request.id,
                paymentKey = request.paymentKey,
                amount = request.amount,
                status = request.status,
                reason = request.reason,
                // TODO: 추후 paymentRequest의 생성 시각을 넣어줘야함.
                requestedAt = LocalDateTime.now()
            )
        ) ?: throw IllegalArgumentException(
            "Payment Transaction not save"
        )
    }
}
