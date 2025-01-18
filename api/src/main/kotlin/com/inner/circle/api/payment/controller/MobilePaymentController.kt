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
@RequestMapping("/api/v1/payments")
class MobilePaymentController(
    private val mobilePaymentUseCase: MobilePaymentUseCase
) : MobilePaymentApi {
    @Operation(summary = "모바일 결제 요청")
    @PostMapping("/{order_id}/proceed/simple")
    override fun proceedMobilePayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<MobilePaymentDto>> {
        val response =
            PaymentResponse.ok(
                MobilePaymentDto.of(
                    mobilePaymentUseCase.confirmPayment(
                        MobilePaymentUseCase.Request(
                            orderId,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                    )
                )
            )
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @Operation(summary = "모바일 결제 요청")
    @PostMapping("/{order_id}/proceed")
    override fun proceedMobilePayment(
        @PathVariable("order_id") orderId: String,
        @RequestBody userCardDto: UserCardDto
    ): ResponseEntity<PaymentResponse<MobilePaymentDto>> {
        val response =
            PaymentResponse.ok(
                MobilePaymentDto.of(
                    mobilePaymentUseCase.confirmPayment(
                        MobilePaymentUseCase.Request(
                            orderId,
                            userCardDto.userId,
                            userCardDto.representativeYn,
                            userCardDto.cardNumber,
                            userCardDto.expirationPeriod,
                            userCardDto.cvc
                        )
                    )
                )
            )
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @Operation(summary = "모바일 결제 취소")
    @GetMapping("/{order_id}/cancel")
    override fun cancelMobilePayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
        val response = PaymentResponse.ok("결제가 취소되었습니다.")
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}
