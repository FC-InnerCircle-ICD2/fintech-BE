package com.inner.circle.api.payment.controller

import com.inner.circle.api.util.serializer.ApiUrl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ApiUrl.V1_PAYMENTS_API_PREFIX)
class PaymentProcedureController {
    // TODO
    // 결제 정보를 확인 한 고객은 확인 버튼을 누름
    // 확인 버튼을 누르게 되면 카드 인증 절차로 넘어간다.
    @PostMapping("/{orderId}/proceed")
    @ResponseStatus(HttpStatus.CREATED)
    fun proceedOrder(
        @PathVariable(name = "orderId") orderId: String
    ) {
    }
}
