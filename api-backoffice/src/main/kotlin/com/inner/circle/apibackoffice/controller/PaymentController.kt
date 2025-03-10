package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.config.SwaggerConfig
import com.inner.circle.apibackoffice.controller.dto.BackofficeResponse
import com.inner.circle.apibackoffice.controller.dto.PaymentWithTransactionsDto
import com.inner.circle.apibackoffice.controller.dto.PaymentsWithTransactionsDto
import com.inner.circle.apibackoffice.controller.dto.TransactionDto
import com.inner.circle.apibackoffice.controller.dto.TransactionStatus
import com.inner.circle.apibackoffice.controller.dto.convertCoreTransactionStatus
import com.inner.circle.apibackoffice.controller.request.CancelPaymentRequest
import com.inner.circle.corebackoffice.security.MerchantUserDetails
import com.inner.circle.corebackoffice.usecase.CancelPaymentUseCase
import com.inner.circle.corebackoffice.usecase.GetPaymentWithTransactionsUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Validated
@Tag(name = "Payment", description = "Payment API")
@BackofficeV1Api
@SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
class PaymentController(
    private val getPaymentWithTransactionsUseCase: GetPaymentWithTransactionsUseCase,
    private val cancelPaymentUseCase: CancelPaymentUseCase
) {
    @Operation(summary = "Payment, Transactions 조회")
    @GetMapping("/payments")
    fun getPayments(
        @AuthenticationPrincipal merchant: MerchantUserDetails,
        @RequestParam("paymentKey") paymentKey: String?,
        @RequestParam("status") status: TransactionStatus?,
        @Parameter(example = "2025-02-15")
        @RequestParam("startDate") startDate: LocalDate?,
        @Parameter(example = "2025-02-19")
        @RequestParam("endDate") endDate: LocalDate?,
        @RequestParam("page", defaultValue = "0") @Min(0) page: Int,
        @RequestParam("limit", defaultValue = "10") @Min(1) @Max(20) limit: Int
    ): BackofficeResponse<PaymentsWithTransactionsDto> {
        val request =
            GetPaymentWithTransactionsUseCase.FindAllByMerchantIdRequest(
                merchantId = merchant.getId(),
                paymentKey = paymentKey,
                status = status?.convertCoreTransactionStatus(),
                startDate = startDate?.toKotlinLocalDate(),
                endDate = endDate?.toKotlinLocalDate(),
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

    @Operation(summary = "결제 취소")
    @PostMapping("/payments/{paymentKey}/cancel")
    fun cancel(
        @AuthenticationPrincipal merchant: MerchantUserDetails,
        @PathVariable("paymentKey") paymentKey: String,
        @Valid @RequestBody request: CancelPaymentRequest
    ): BackofficeResponse<TransactionDto> {
        val transaction =
            cancelPaymentUseCase.cancel(
                CancelPaymentUseCase.CancelPaymentRequest(
                    merchantId = merchant.getId(),
                    paymentKey = paymentKey,
                    amount = request.amount
                )
            )
        return BackofficeResponse.ok(TransactionDto.of(transaction))
    }
}
