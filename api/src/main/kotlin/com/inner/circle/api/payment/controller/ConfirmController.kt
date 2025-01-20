package com.inner.circle.api.payment.controller

import com.inner.circle.api.payment.interceptor.RequireAuth
import com.inner.circle.api.request.PaymentApproveRequest
import com.inner.circle.api.response.CardApproveResponse
import com.inner.circle.api.response.PaymentApproveResponse
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.service.dto.PaymentRequestDto
import com.inner.circle.core.usecase.GetPaymentRequestUseCase
import com.inner.circle.core.usecase.HttpUseCase
import com.inner.circle.core.usecase.SavePaymentUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class ConfirmController  (
    private val httpCardApprove:HttpUseCase,
    private val getPaymentRequestUseCase: GetPaymentRequestUseCase,
    private val savePaymentUseCase: SavePaymentUseCase,

    ): ConfirmApi{
    @Value("\${card.url.baseUrl}")
    lateinit var cardBaseUrl: String

    @Value("\${card.url.approveEndPoint}")
    lateinit var cardApproveEndPoint: String

    @RequireAuth
    @PostMapping("/confirm")
    override fun confirmPayment(
        @RequestBody paymentApproveRequest: PaymentApproveRequest
    ): ResponseEntity<PaymentResponse<PaymentApproveResponse>> {
        val paymentRequest: PaymentRequestDto = getPaymentRequestUseCase.getPaymentRequest(
            GetPaymentRequestUseCase.Request(
                paymentApproveRequest.paymentKey
            )
        )
        
        //카드 승인
        val map1 = mapOf("cardNumber" to (paymentRequest?.cardNumber ?: ""), "amount" to paymentRequest.amount)
        val responseCardApprove: Map<String, Any> = httpCardApprove.httpPostRequest(cardBaseUrl, cardApproveEndPoint, map1);

        if(responseCardApprove["isValid"] as Boolean) {
            savePaymentUseCase.save(
                SavePaymentUseCase.Request(
                    paymentRequest.paymentKey,
                    "KRW",
                    paymentRequest.userId,
                    paymentRequest.merchantId,
                    "APPROVED",
                    paymentRequest.orderId ,
                    paymentRequest.orderName
                )
            )
        }

        return ResponseEntity.ok(
            PaymentResponse.ok(
                PaymentApproveResponse(
                    order_id = paymentApproveRequest.order_id,
                    paymentKey= paymentApproveRequest.paymentKey,
                    amount= paymentApproveRequest.amount.toBigDecimal()
                )
            )
        )
    }
}
