package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.PaymentDto
import com.inner.circle.apibackoffice.controller.dto.TransactionDto
import com.inner.circle.apibackoffice.controller.dto.TransactionsDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.ResponsePaymentUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Payment", description = "Payment API")
@RestController
@RequestMapping("/b/v1")
class PaymentController(
    private val responsePaymentUseCase: ResponsePaymentUseCase
) {
    @Operation(summary = "Payment 조회")
    @GetMapping("/payments/{payment_key}")
    fun getPayment(
        @PathVariable("payment_key") paymentKey: String
    ): BackofficeResponse<PaymentDto> {
        val request = ResponsePaymentUseCase.Request(paymentKey)
        return BackofficeResponse.ok(
            PaymentDto.of(responsePaymentUseCase.getPayment(request))
        )
    }

    @Operation(summary = "Payment Key를 이용한 Transactions 조회")
    @GetMapping("/payments/{payment_key}/transactions")
    fun getTransactions(
        @PathVariable("payment_key") paymentKey: String
    ): BackofficeResponse<TransactionsDto> {
        val request = ResponsePaymentUseCase.Request(paymentKey)
        return BackofficeResponse.ok(
            TransactionsDto(
                responsePaymentUseCase
                    .getTransactions(request)
                    .map { transaction -> TransactionDto.of(transaction) }
                    .toList()
            )
        )
    }
}
