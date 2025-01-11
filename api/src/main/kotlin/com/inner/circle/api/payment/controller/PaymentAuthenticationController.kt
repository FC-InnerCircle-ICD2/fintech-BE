package com.inner.circle.api.payment.controller

import com.inner.circle.api.util.UrlConstants
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UrlConstants.PAYMENT_V1_API_URL_PREFIX)
class PaymentAuthenticationController {
    @PostMapping("{orderId}/proceed")
    fun proceedAuthentication(
        @PathVariable(name = "orderId") orderId: String
    ) {}
}
