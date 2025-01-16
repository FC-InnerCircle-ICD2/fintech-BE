package com.inner.circle.api.payment.controller

import com.inner.circle.api.payment.interceptor.RequireAuth
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.structure.service.ClaimService
import com.inner.circle.core.structure.usecase.PaymentClaimRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class ClaimController(
    private val claimService: ClaimService
) : ClaimApi {
    @RequireAuth
    @PostMapping
    override fun createPayment(
        @RequestBody request: PaymentClaimRequest,
        @RequestAttribute("merchantId") merchantId: String
    ): ResponseEntity<PaymentResponse<String>> {
        val response = claimService.createPayment(request)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @RequireAuth
    @PostMapping("/{order_id}/proceed")
    override fun proceedPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
//           val response = paymentService.proceedPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment proceeded.")
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @RequireAuth
    @PostMapping("/{order_id}/cancel")
    override fun cancelPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
//            val response = paymentService.cancelPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment cancelled.")
        return ResponseEntity(response, HttpStatus.OK)
    }
}
