package com.inner.circle.api.payment.claims.controller

import com.inner.circle.core.payment.claims.dto.PaymentResponse
import com.inner.circle.core.payment.claims.dto.request.PaymentClaimRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class ClaimController(
//    val claimService : ClaimService
) {
    @PostMapping
    fun createPayment(
        @RequestBody request: PaymentClaimRequest
    ): ResponseEntity<PaymentResponse<Nothing?>> {
//           val response = paymentService.createPayment(request)
        val response = PaymentResponse.ok(null)
        return ResponseEntity(response, HttpStatus.OK)
    }
}
