package com.inner.circle.api.controller.user

import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.api.config.SwaggerConfig
import com.inner.circle.api.controller.PaymentForUserV1Api
import com.inner.circle.api.controller.dto.CancelPaymentDto
import com.inner.circle.api.controller.dto.ConfirmPaymentDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.dto.UserCardDto
import com.inner.circle.api.controller.request.CancelPaymentRequest
import com.inner.circle.api.controller.request.ConfirmPaymentRequest
import com.inner.circle.api.controller.request.ConfirmSimplePaymentRequest
import com.inner.circle.api.controller.request.UserCardRequest
import com.inner.circle.core.security.AccountDetails
import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto
import com.inner.circle.core.usecase.CancelPaymentUseCase
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.core.usecase.PaymentTokenHandlingUseCase
import com.inner.circle.core.usecase.UserCardUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.inner.circle.core.service.dto.UserCardDto as CoreUserCardDto

@Tag(name = "Payments - User", description = "결제 고객(App) 결제 관련 API")
@PaymentForUserV1Api
@SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
class UserPaymentController(
    private val paymentTokenHandlingUseCase: PaymentTokenHandlingUseCase,
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    private val userCardUseCase: UserCardUseCase,
    private val cancelPaymentUseCase: CancelPaymentUseCase,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    private val logger: Logger = LoggerFactory.getLogger(UserPaymentController::class.java)

    @Operation(summary = "간편 결제 인증")
    @PostMapping("/authentication/simple")
    fun proceedPaymentConfirm(
        @AuthenticationPrincipal account: AccountDetails,
        @RequestBody confirmSimplePaymentRequest: ConfirmSimplePaymentRequest
    ): PaymentResponse<ConfirmPaymentDto> {
        val accountId = account.id
        val foundPaymentToken =
            paymentTokenHandlingUseCase.findPaymentToken(
                confirmSimplePaymentRequest.token
            )
        val orderId = foundPaymentToken.orderId
        val merchantId = foundPaymentToken.merchantId

        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_VERIFICATE,
            orderId = orderId,
            merchantId = merchantId
        )

        val authResult =
            confirmPaymentUseCase.confirmPayment(
                ConfirmSimplePaymentUseCase.Request(
                    orderId = orderId,
                    merchantId = merchantId,
                    accountId = accountId
                )
            )

        val data =
            ConfirmPaymentDto.of(
                authResult
            )
        val response =
            PaymentResponse.ok(
                data
            )

        sendAuthResultMessage(authResult)
        return response
    }

    @Deprecated("일반 결제 기능 제외 예정")
    @Operation(summary = "일반 결제 인증")
    @PostMapping("/authentication")
    fun proceedPaymentConfirm(
        @RequestBody confirmPaymentRequest: ConfirmPaymentRequest
    ): PaymentResponse<ConfirmPaymentDto> {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_VERIFICATE,
            orderId = confirmPaymentRequest.orderId,
            merchantId = confirmPaymentRequest.merchantId
        )

        val response =
            PaymentResponse.ok(
                ConfirmPaymentDto.of(
                    confirmPaymentUseCase.confirmPayment(
                        ConfirmPaymentUseCase.Request(
                            orderId = confirmPaymentRequest.orderId,
                            merchantId = confirmPaymentRequest.merchantId,
                            cardNumber = confirmPaymentRequest.cardNumber,
                            expirationPeriod = confirmPaymentRequest.expirationPeriod,
                            cvc = confirmPaymentRequest.cvc
                        )
                    )
                )
            )

        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_PROGRESS,
            orderId = confirmPaymentRequest.orderId,
            merchantId = confirmPaymentRequest.merchantId
        )
        return response
    }

    @Operation(summary = "결제 취소")
    @PostMapping("/cancel")
    fun cancelPaymentConfirmWithOrderId(
        @AuthenticationPrincipal account: AccountDetails,
        @RequestBody cancelPaymentRequest: CancelPaymentRequest
    ): PaymentResponse<CancelPaymentDto> {
        val accountId = account.id
        val foundPaymentToken =
            paymentTokenHandlingUseCase.findPaymentToken(
                cancelPaymentRequest.token
            )
        val orderId = foundPaymentToken.orderId
        val merchantId = foundPaymentToken.merchantId

        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_VERIFICATE,
            orderId = orderId,
            merchantId = merchantId
        )

        val cancelResult =
            cancelPaymentUseCase.cancelPayment(
                CancelPaymentUseCase.Request(
                    orderId = orderId,
                    merchantId = merchantId,
                    accountId = accountId
                )
            )

        val data =
            CancelPaymentDto.of(
                cancelResult
            )
        val response =
            PaymentResponse.ok(
                data
            )

        sendStatusChangedMessage(
            status = PaymentStatusEventType.CANCELED,
            orderId = orderId,
            merchantId = merchantId
        )

        return response
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
                    merchantId = merchantId
                )
            )
        } catch (e: Exception) {
            logger.error("Error while send ${status.name} Status.", e)
        }
    }

    private fun sendAuthResultMessage(authResult: ConfirmPaymentCoreDto) {
        val inProgress = PaymentStatusEventType.IN_PROGRESS
        try {
            statusChangedMessageSender.sendPaymentAuthResultMessage(
                inProgress,
                authResult
            )
        } catch (e: Exception) {
            logger.error("Error while send ${inProgress.name} Status.", e)
        }
    }

    @Operation(summary = "유저 카드 등록")
    @PostMapping("/cards")
    fun registerCard(
        @AuthenticationPrincipal account: AccountDetails,
        @RequestBody request: UserCardRequest
    ): PaymentResponse<UserCardDto> {
        val result =
            userCardUseCase.save(
                CoreUserCardDto(
                    id = null,
                    accountId = account.id,
                    isRepresentative = request.isRepresentative,
                    cardNumber = request.cardNumber,
                    expirationPeriod = request.expirationPeriod,
                    cvc = request.cvc
                )
            )
        return PaymentResponse.ok(
            UserCardDto(
                id = result.id,
                accountId = result.accountId,
                isRepresentative = result.isRepresentative,
                cardNumber = result.cardNumber,
                expirationPeriod = result.expirationPeriod,
                cvc = result.cvc
            )
        )
    }

    @Operation(summary = "유저의 카드 목록 조회")
    @GetMapping("/cards")
    fun getUserCard(
        @AuthenticationPrincipal account: AccountDetails
    ): PaymentResponse<List<UserCardDto>> {
        val coreUserCardDtoList = userCardUseCase.findByAccountId(account.id)
        return PaymentResponse.ok(
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
