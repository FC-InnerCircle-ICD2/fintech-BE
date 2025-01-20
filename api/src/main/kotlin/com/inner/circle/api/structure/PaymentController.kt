package com.inner.circle.api.structure

import com.inner.circle.api.structure.dto.PaymentRequest
import com.inner.circle.core.structure.usecase.RequestPaymentUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


class PaymentController(
    private val paymentUseCase: RequestPaymentUseCase
) {
    @PostMapping("")
    fun payment(
        @RequestBody request: PaymentRequest
    ) {
        paymentUseCase.payment(
            RequestPaymentUseCase.Request(
                request.userName,
                request.amount
            )
        )
    }
}
