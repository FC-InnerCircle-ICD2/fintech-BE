package com.inner.circle.core.usecase

import com.inner.circle.core.domain.TransactionStatus
import com.inner.circle.core.service.dto.PaymentWithTransactionsDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

interface GetPaymentWithTransactionsUseCase {
    data class FindAllByAccountIdRequest(
        val accountId: Long,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val status: TransactionStatus?,
        val page: Int,
        val limit: Int
    ) {
        init {
            startDate?.let { start ->
                endDate?.let { end ->
                    val currentDate =
                        java.time.LocalDate
                            .now()
                            .toKotlinLocalDate()
                    require(start <= end) { "startDate must be less than or equal to endDate" }
                    require(
                        end <= currentDate
                    ) { "endDate must be less than or equal to the current date" }
                }
            }
        }
    }

    data class FindByPaymentKeyRequest(
        val accountId: Long,
        val paymentKey: String
    )

    fun findAllByAccountId(request: FindAllByAccountIdRequest): List<PaymentWithTransactionsDto>

    fun findByPaymentKey(request: FindByPaymentKeyRequest): PaymentWithTransactionsDto
}
