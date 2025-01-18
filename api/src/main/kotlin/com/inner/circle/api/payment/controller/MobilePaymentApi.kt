package com.inner.circle.api.payment.controller

import com.inner.circle.api.payment.controller.dto.MobilePaymentDto
import com.inner.circle.api.payment.controller.dto.PaymentResponse
import com.inner.circle.api.payment.controller.dto.UserCardDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface MobilePaymentApi {
    @Operation(summary = "Proceed a mobile payment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Payment proceeded",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PaymentResponse::class)
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()])
        ]
    )
    fun proceedMobilePayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<MobilePaymentDto>>

    @Operation(summary = "Proceed a mobile payment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Payment proceeded",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PaymentResponse::class)
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()])
        ]
    )
    fun proceedMobilePayment(
        @PathVariable("order_id") orderId: String,
        @RequestBody userCardDto: UserCardDto
    ): ResponseEntity<PaymentResponse<MobilePaymentDto>>

    @Operation(summary = "Cancel a mobile payment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Payment cancelled",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PaymentResponse::class)
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()])
        ]
    )

    fun cancelMobilePayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>>
}
