package com.inner.circle.api.controller.merchant

import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.api.config.SwaggerConfig
import com.inner.circle.api.controller.PaymentForMerchantV1Api
import com.inner.circle.api.controller.dto.PaymentApproveDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.dto.TransactionDto
import com.inner.circle.api.controller.dto.UserCardDto
import com.inner.circle.api.controller.request.CancelPaymentRequest
import com.inner.circle.api.controller.request.PaymentApproveRequest
import com.inner.circle.api.controller.request.PaymentClaimRequest
import com.inner.circle.core.security.MerchantUserDetails
import com.inner.circle.core.usecase.CancelPaymentUseCase
import com.inner.circle.core.usecase.PaymentClaimUseCase
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import com.inner.circle.core.usecase.UserCardUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payments - Merchant", description = "상점 고객(SDK) 결제 관련 API")
@PaymentForMerchantV1Api
@SecurityRequirement(name = SwaggerConfig.BASIC_AUTH)
class MerchantPaymentController(
    private val claimUseCase: PaymentClaimUseCase,
    private val savePaymentApproveService: SavePaymentApproveUseCase,
    private val userCardUseCase: UserCardUseCase,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender,
    private val cancelPaymentUseCase: CancelPaymentUseCase
) {
    private val logger: Logger = LoggerFactory.getLogger(MerchantPaymentController::class.java)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Operation(summary = "결제 요청")
    @PostMapping
    fun createPayment(
        @AuthenticationPrincipal merchantUserDetails: MerchantUserDetails,
        @Valid @RequestBody request: PaymentClaimRequest
    ): PaymentResponse<PaymentClaimUseCase.PaymentClaimResponse> {
        val merchantId = merchantUserDetails.getId()
        val merchantName = merchantUserDetails.getName()

        val claimRequest =
            PaymentClaimUseCase.ClaimRequest(
                amount = request.amount,
                orderId = request.orderId,
                orderName = request.orderName
            )

        val response = claimUseCase.createPayment(claimRequest, merchantId, merchantName)

        sendStatusChangedMessage(
            status = PaymentStatusEventType.READY,
            orderId = request.orderId,
            merchantId = merchantId
        )

        return PaymentResponse.ok(response)
    }

    @Operation(summary = "결제 승인")
    @PostMapping("/confirm")
    fun confirmPayment(
        @AuthenticationPrincipal merchantUserDetails: MerchantUserDetails,
        @Valid @RequestBody paymentApproveRequest: PaymentApproveRequest
    ): PaymentResponse<PaymentApproveDto> {
        val merchantId = merchantUserDetails.getId()

        val data =
            savePaymentApproveService
                .saveApprove(
                    SavePaymentApproveUseCase.Request(
                        paymentKey = paymentApproveRequest.paymentKey,
                        orderId = paymentApproveRequest.orderId,
                        amount = paymentApproveRequest.amount
                    )
                ).let {
                    PaymentApproveDto(
                        orderId = it.orderId,
                        paymentKey = it.paymentKey,
                        amount = it.amount
                    )
                }

        sendStatusChangedMessage(
            status = PaymentStatusEventType.DONE,
            orderId = paymentApproveRequest.orderId,
            merchantId = merchantId
        )

        coroutineScope.launch {
            val targetMerchantId = merchantId.toString()
            val targetOrderId = paymentApproveRequest.orderId
            try {
                delay(60000L)
                statusChangedMessageSender.removeMerchantSession(targetMerchantId, targetOrderId)
                delay(60000L)
                statusChangedMessageSender.removeAllSessions(targetMerchantId, targetOrderId)
            } catch (e: Exception) {
                logger.error(
                    "Error while payment sse session on complete. (orderId : {}",
                    targetOrderId,
                    e
                )
            }
        }

        return PaymentResponse.ok(
            data
        )
    }

    @Operation(summary = "결제 취소")
    @PostMapping("/payments/{paymentKey}/cancel")
    fun cancelPayment(
        @AuthenticationPrincipal merchantUserDetails: MerchantUserDetails,
        @PathVariable("paymentKey") paymentKey: String,
        @Valid @RequestBody request: CancelPaymentRequest
    ): PaymentResponse<TransactionDto> {
        val transaction =
            cancelPaymentUseCase.cancel(
                CancelPaymentUseCase.CancelPaymentRequest(
                    merchantId = merchantUserDetails.getId(),
                    paymentKey = paymentKey,
                    amount = request.amount
                )
            )

        return PaymentResponse.ok(TransactionDto.of(transaction))
    }

    private fun sendStatusChangedMessage(
        status: PaymentStatusEventType,
        orderId: String,
        merchantId: Long
    ) {
        try {
            statusChangedMessageSender.sendProcessChangedMessage(
                PaymentStatusChangedSsePaymentRequest(
                    eventType = status.getEventType(),
                    status = status.name,
                    orderId = orderId,
                    merchantId = merchantId.toString()
                )
            )
        } catch (e: Exception) {
            logger.error("Error while send ${status.name} Status.", e)
        }
    }

    @Operation(summary = "[테스트 용도] - 모든 유저의 카드 목록 조회")
    @GetMapping("/cards")
    fun getAllCard(): PaymentResponse<List<UserCardDto>> {
        val coreUserCardDtoList = userCardUseCase.findAll()
        return PaymentResponse.ok(
            coreUserCardDtoList
                .map { coreUserCardDto ->
                    UserCardDto(
                        id = coreUserCardDto.id.toString(),
                        accountId = coreUserCardDto.accountId.toString(),
                        isRepresentative = coreUserCardDto.isRepresentative,
                        cardNumber = coreUserCardDto.cardNumber,
                        expirationPeriod = coreUserCardDto.expirationPeriod,
                        cvc = coreUserCardDto.cvc,
                        cardCompany = coreUserCardDto.cardCompany
                    )
                }.toList()
        )
    }
}
