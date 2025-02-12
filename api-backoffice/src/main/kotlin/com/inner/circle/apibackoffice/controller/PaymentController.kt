package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.PaymentDto
import com.inner.circle.apibackoffice.controller.dto.TransactionDto
import com.inner.circle.apibackoffice.controller.dto.TransactionsDto
import com.inner.circle.apibackoffice.controller.dto.UserCardDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.GetPaymentUseCase
import com.inner.circle.corebackoffice.usecase.GetTransactionUseCase
import com.inner.circle.corebackoffice.usecase.UserCardUseCase
import com.inner.circle.corebackoffice.service.dto.UserCardDto as CoreUserCardDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payment", description = "Payment API")
@BackofficeV1Api
class PaymentController(
    private val getPaymentUseCase: GetPaymentUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val userCardUseCase: UserCardUseCase
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

    @Operation(summary = "카드 등록")
    @PostMapping("/payments/register/card")
    fun registerCard(
        @RequestBody request: UserCardDto
    ): BackofficeResponse<UserCardDto> {
        userCardUseCase.save(
            CoreUserCardDto(
                id = null,
                accountId = request.accountId,
                isRepresentative = request.isRepresentative,
                cardNumber = request.cardNumber,
                expirationPeriod = request.expirationPeriod,
                cvc = request.cvc
            )
        )
        return BackofficeResponse.ok(request)
    }

    @Operation(summary = "모든 카드 조회")
    @GetMapping("/payments/all/card")
    fun getAllCard(): BackofficeResponse<List<UserCardDto>> {
        val coreUserCardDtoList = userCardUseCase.findAll()
        return BackofficeResponse.ok(
            coreUserCardDtoList
                .map { coreUserCardDto ->
                    UserCardDto(
                        id = coreUserCardDto.id,
                        accountId = coreUserCardDto.accountId,
                        isRepresentative = coreUserCardDto.isRepresentative,
                        cardNumber = coreUserCardDto.cardNumber,
                        expirationPeriod = coreUserCardDto.expirationPeriod,
                        cvc = coreUserCardDto.cvc
                    )
            }.toList()
        )
    }

    @Operation(summary = "유저 카드 조회")
    @GetMapping("/payments/{accountId}/card")
    fun getUserCard(
        @PathVariable("accountId") accountId: Long
    ): BackofficeResponse<List<UserCardDto>> {
        val coreUserCardDtoList = userCardUseCase.findByAccountId(accountId)
        return BackofficeResponse.ok(
            coreUserCardDtoList
                .map { coreUserCardDto ->
                    UserCardDto(
                        id = coreUserCardDto.id,
                        accountId = coreUserCardDto.accountId,
                        isRepresentative = coreUserCardDto.isRepresentative,
                        cardNumber = coreUserCardDto.cardNumber,
                        expirationPeriod = coreUserCardDto.expirationPeriod,
                        cvc = coreUserCardDto.cvc
                    )
                }.toList()
        )
    }
}
