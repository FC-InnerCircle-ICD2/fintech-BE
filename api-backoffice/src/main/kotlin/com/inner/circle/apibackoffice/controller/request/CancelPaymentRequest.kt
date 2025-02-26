package com.inner.circle.apibackoffice.controller.request

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import java.math.BigDecimal

data class CancelPaymentRequest(
    @field:DecimalMin(value = "0.01", message = "금액은 0원 이하로는 입력될 수 없습니다.")
    @field:DecimalMax(value = "100000000", message = "최대 1억까지 결제를 요청할 수 있습니다.")
    val amount: BigDecimal
)
