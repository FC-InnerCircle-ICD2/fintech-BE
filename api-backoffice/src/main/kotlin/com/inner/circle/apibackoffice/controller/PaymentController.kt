package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.PaymentWithTransactionsDto
import com.inner.circle.apibackoffice.controller.dto.PaymentsWithTransactionsDto
import com.inner.circle.apibackoffice.controller.dto.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.GetPaymentWithTransactionsUseCase
import com.inner.circle.corebackoffice.security.MerchantUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Payment", description = "Payment API")
@BackofficeV1Api
class PaymentController(
    private val getPaymentWithTransactionsUseCase: GetPaymentWithTransactionsUseCase
) {
    @Operation(summary = "Payment, Transactions 조회")
    @GetMapping("/payments")
    fun getPayments(
        @AuthenticationPrincipal merchant: MerchantUserDetails,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("limit", defaultValue = "20") limit: Int
    ): BackofficeResponse<PaymentsWithTransactionsDto> {
        val request =
            GetPaymentWithTransactionsUseCase.FindAllByMerchantIdRequest(
                merchantId = merchant.getId(),
                page = page,
                limit = limit
            )

        return BackofficeResponse.ok(
            PaymentsWithTransactionsDto(
                payments =
                    getPaymentWithTransactionsUseCase
                        .findAllByMerchantId(request)
                        .map { paymentWithTransactionsDto ->
                            PaymentWithTransactionsDto.of(
                                paymentWithTransactionsDto
                            )
                        }.toList()
            )
        )
    }

    @Operation(summary = "Payment Key를 이용한 Transactions 조회")
    @GetMapping("/payments/{paymentKey}/transactions")
    fun getTransactionsByPaymentKey(
        @AuthenticationPrincipal merchant: MerchantUserDetails,
        @PathVariable("paymentKey") paymentKey: String
    ): BackofficeResponse<PaymentWithTransactionsDto> {
        val request =
            GetPaymentWithTransactionsUseCase.FindByPaymentKeyRequest(
                merchantId = merchant.getId(),
                paymentKey = paymentKey
            )
        return BackofficeResponse.ok(
            PaymentWithTransactionsDto.of(
                getPaymentWithTransactionsUseCase
                    .findByPaymentKey(request)
            )
        )
    }
}
