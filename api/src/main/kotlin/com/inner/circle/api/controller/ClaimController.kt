package com.inner.circle.api.controller

import com.inner.circle.api.interceptor.RequireAuth
import com.inner.circle.api.structure.dto.PaymentClaimRequest
import com.inner.circle.api.structure.dto.PaymentResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

class ClaimController : ClaimApi {
    @RequireAuth
    @PostMapping
    override fun createPayment(
        @RequestBody request: PaymentClaimRequest
    ): ResponseEntity<PaymentResponse<String>> {
//           val response = paymentService.createPayment(request)
        val response = PaymentResponse.ok("payment request accepted..")
        return ResponseEntity(response, HttpStatus.OK)
    }

    @RequireAuth
    @PostMapping("/{order_id}/proceed/example")
    override fun proceedPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
//           val response = paymentService.proceedPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment proceeded.")
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @RequireAuth
    @PostMapping("/{order_id}/cancel/example")
    override fun cancelPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
//            val response = paymentService.cancelPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment cancelled.")
        return ResponseEntity(response, HttpStatus.OK)
    }
}
