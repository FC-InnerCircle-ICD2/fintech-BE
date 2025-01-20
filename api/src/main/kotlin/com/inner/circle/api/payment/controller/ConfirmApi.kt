package com.inner.circle.api.payment.controller

import com.inner.circle.api.request.PaymentApproveRequest
import com.inner.circle.api.response.PaymentApproveResponse
import com.inner.circle.api.structure.dto.PaymentClaimRequest
import com.inner.circle.api.structure.dto.PaymentResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface ConfirmApi {
    @Operation(summary = "Cancel a payment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
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
    fun confirmPayment(
        @RequestBody paymentApproveRequest: PaymentApproveRequest
    ): ResponseEntity<PaymentResponse<PaymentApproveResponse>>
}
