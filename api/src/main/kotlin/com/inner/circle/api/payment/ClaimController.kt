package com.inner.circle.api.payment

import com.inner.circle.api.structure.dto.PaymentClaimRequest
import com.inner.circle.api.structure.dto.PaymentResponse
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
