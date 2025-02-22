package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.service.dto.PaymentWithTransactionsDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

interface GetPaymentWithTransactionsUseCase {
    data class FindAllByMerchantIdRequest(
        val merchantId: Long,
        val paymentKey: String?,
        val status: TransactionStatus?,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
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
        val merchantId: Long,
        val paymentKey: String
    )

    fun findAllByMerchantId(request: FindAllByMerchantIdRequest): List<PaymentWithTransactionsDto>

    fun findByPaymentKey(request: FindByPaymentKeyRequest): PaymentWithTransactionsDto
}
