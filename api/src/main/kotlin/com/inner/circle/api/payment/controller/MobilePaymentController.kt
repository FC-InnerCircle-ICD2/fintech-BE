package com.inner.circle.api.payment.controller

import com.inner.circle.api.payment.controller.dto.MobilePaymentDto
import com.inner.circle.api.payment.controller.dto.PaymentResponse
import com.inner.circle.api.payment.controller.dto.UserCardDto
import com.inner.circle.core.structure.usecase.MobilePaymentUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "MobilePayment", description = "MobilePayment API")
@RestController
@PaymentV1Api
class MobilePaymentController(
    private val mobilePaymentUseCase: MobilePaymentUseCase
) {

    @Operation(summary = "모바일 간편 결제 요청")
    @PostMapping("/proceed/simple/{order_id}")
    fun proceedMobilePayment(
        @PathVariable("order_id") orderId: String
    ): PaymentResponse<MobilePaymentDto> {
        val response =
            PaymentResponse.ok(
                MobilePaymentDto.of(
                    mobilePaymentUseCase.confirmPayment(
                        MobilePaymentUseCase.Request(
                            orderId = orderId,
                            userId = null,
                            representativeYn = null,
                            cardNumber = null,
                            expirationPeriod = null,
                            cvc = null
                        )
                    )
                )
            )
        return response
    }

    @Operation(summary = "모바일 결제 요청")
    @PostMapping("/proceed/{order_id}")
    fun proceedMobilePayment(
        @PathVariable("order_id") orderId: String,
        @RequestBody userCardDto: UserCardDto
    ): PaymentResponse<MobilePaymentDto> {
        val response =
            PaymentResponse.ok(
                MobilePaymentDto.of(
                    mobilePaymentUseCase.confirmPayment(
                        MobilePaymentUseCase.Request(
                            orderId = orderId,
                            userId = userCardDto.userId,
                            representativeYn = userCardDto.representativeYn,
                            cardNumber = userCardDto.cardNumber,
                            expirationPeriod = userCardDto.expirationPeriod,
                            cvc = userCardDto.cvc
                        )
                    )
                )
            )
        return response
    }

    @Operation(summary = "모바일 결제 취소")
    @GetMapping("/cancel/{order_id}")
    fun cancelMobilePayment(
        @PathVariable("order_id") orderId: String
    ): PaymentResponse<String> {
        val response = PaymentResponse.ok("결제가 취소되었습니다.")
        return response
    }
}
