package com.inner.circle.api.controller.request

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class PaymentApproveRequest(
    @field:NotBlank(message = "Order ID는 필수 입력 값입니다.")
    @field:Size(max = 100, message = "Order ID는 최대 100자까지 입력할 수 있습니다.")
    val orderId: String,
    @field:NotBlank(message = "paymentKey 는 필수 입력 값입니다.")
    val paymentKey: String,
    @field:DecimalMin(value = "0.01", message = "금액은 0원 이하로는 입력될 수 없습니다.")
    @field:DecimalMax(value = "100000000", message = "최대 1억까지 결제를 요청할 수 있습니다.")
    val amount: BigDecimal
)
