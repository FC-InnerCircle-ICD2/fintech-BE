package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.PaymentDto
import com.inner.circle.apibackoffice.controller.dto.TransactionDto
import com.inner.circle.apibackoffice.controller.dto.TransactionsDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.GetPaymentUseCase
import com.inner.circle.corebackoffice.usecase.GetTransactionUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "Payment", description = "Payment API")
@BackofficeV1Api
class PaymentController(
    private val getPaymentUseCase: GetPaymentUseCase,
    private val getTransactionUseCase: GetTransactionUseCase
) {
    @Operation(summary = "Payment 조회")
    @GetMapping("/payments/{paymentKey}")
    fun getPayment(
        @PathVariable("paymentKey") paymentKey: String
    ): BackofficeResponse<PaymentDto> {
        val request = GetPaymentUseCase.Request(paymentKey)
        return BackofficeResponse.ok(
            PaymentDto.of(getPaymentUseCase.getPaymentByPaymentKey(request))
        )
    }

    @Operation(summary = "Payment Key를 이용한 Transactions 조회")
    @GetMapping("/payments/{paymentKey}/transactions")
    fun getTransactions(
        @PathVariable("paymentKey") paymentKey: String
    ): BackofficeResponse<TransactionsDto> {
        val request = GetTransactionUseCase.Request(paymentKey)
        return BackofficeResponse.ok(
            TransactionsDto(
                getTransactionUseCase
                    .getTransactionsByPaymentKey(request)
                    .map { transaction -> TransactionDto.of(transaction) }
                    .toList()
            )
        )
    }
}
