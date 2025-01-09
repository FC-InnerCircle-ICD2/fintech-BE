package com.inner.circle.api.payment.controller

import com.inner.circle.api.structure.dto.PaymentClaimRequest
import com.inner.circle.api.structure.dto.PaymentResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
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
    ): ResponseEntity<PaymentResponse<String>> {
//           val response = paymentService.createPayment(request)
        val response = PaymentResponse.ok("payment request accepted..")
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/{order_id}/proceed")
    fun proceedPayment(
        @PathVariable("order_id") orderId: String,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<PaymentResponse<String>> {
//           val response = paymentService.proceedPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment proceeded.")
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PostMapping("/{order_id}/cancel")
    fun cancelPayment(
        @PathVariable("order_id") orderId: String,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<PaymentResponse<String>> {
//            val response = paymentService.cancelPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment cancelled.")
        return ResponseEntity(response, HttpStatus.OK)
    }
}
