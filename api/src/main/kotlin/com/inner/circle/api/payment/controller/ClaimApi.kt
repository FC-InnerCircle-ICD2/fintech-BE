package com.inner.circle.api.payment.controller

import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.structure.usecase.PaymentClaimRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody

interface ClaimApi {
    @Operation(summary = "Create a payment request")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Payment request accepted",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PaymentResponse::class),
                        examples = [
                            ExampleObject(
                                value = """
                    {
                        "ok": true,
                        "data": {
                            "order_id": "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6",
                            "expired_at": "2025-01-11T14:48:00",
                            "message": "Payment request sent to PG server."
                        }
                    }
                    """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()]),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PaymentResponse::class),
                        examples = [
                            ExampleObject(
                                value = """
                    {
                        "ok": false,
                        "error": {
                            "code": 403,
                            "message": "forbidden"
                        }
                    }
                    """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun createPayment(
        @RequestBody request: PaymentClaimRequest,
        @RequestAttribute("merchantId") merchantId: String
    ): ResponseEntity<PaymentResponse<String>>

    @Operation(summary = "Proceed a payment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
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
    fun proceedPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>>

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
    fun cancelPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>>
}
