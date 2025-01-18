package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.transaction.TransactionDto
import com.inner.circle.infrabackoffice.port.transaction.FindByPaymentIdPort
import com.inner.circle.infrabackoffice.repository.TransactionRepository
import java.util.UUID
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val repository: TransactionRepository
) : FindByPaymentIdPort {
    override fun findByPaymentId(paymentId: UUID): List<TransactionDto> =
        repository
            .findByPaymentId(paymentId)
            .map {
                TransactionDto(
                    it.id!!,
                    it.paymentId,
                    it.amount,
                    it.status,
                    it.reason,
                    it.requestedAt.toKotlinLocalDateTime(),
                    it.completedAt.toKotlinLocalDateTime(),
                    it.createdAt.toKotlinLocalDateTime(),
                    it.updatedAt.toKotlinLocalDateTime()
                )
            }.toList()
}
