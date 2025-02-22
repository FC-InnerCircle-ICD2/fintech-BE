package com.inner.circle.api.controller.request

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class PaymentClaimRequest(
    @field:DecimalMin(value = "0.01", message = "금액은 0원 이하로는 입력될 수 없습니다.")
    @field:DecimalMax(value = "100000000", message = "최대 1억까지 결제를 요청할 수 있습니다.")
    val amount: BigDecimal,
    @field:NotBlank(message = "Order ID는 필수 입력 값입니다.")
    @field:Size(max = 100, message = "Order ID는 최대 100자까지 입력할 수 있습니다.")
    val orderId: String,
    @field:NotNull(message = "Order Name (주문서 이름)은 null이 허용되지 않습니다.")
    @field:Size(max = 100, message = "Order Name (주문서 이름)은 최대 100자까지 입력할 수 있습니다.")
    val orderName: String
)
