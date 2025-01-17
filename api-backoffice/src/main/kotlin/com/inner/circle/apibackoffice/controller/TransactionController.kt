package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.TransactionDto
import com.inner.circle.apibackoffice.controller.dto.TransactionsDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.ResponseTransactionUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Transaction", description = "Transaction API")
@RestController
@RequestMapping("/b/v1")
class TransactionController(
    private val responseTransactionUseCase: ResponseTransactionUseCase
) {
    @Operation(summary = "Transaction 조회")
    @GetMapping("/transactions/{payment_id}")
    fun getTransactions(
        @PathVariable("payment_id") paymentId: String
    ): BackofficeResponse<TransactionsDto> {
        val request =
            ResponseTransactionUseCase.Request(
                UUID.fromString(paymentId)
            )
        return BackofficeResponse.ok(
            TransactionsDto(
                responseTransactionUseCase
                    .getTransactions(request)
                    .map { transaction -> TransactionDto.of(transaction) }
                    .toList()
            )
        )
    }
}
