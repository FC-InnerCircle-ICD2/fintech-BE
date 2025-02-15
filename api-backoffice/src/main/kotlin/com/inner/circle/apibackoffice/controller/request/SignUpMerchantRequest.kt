package com.inner.circle.apibackoffice.controller.request

data class SignUpMerchantRequest(
    val email: String,
    val password: String,
    val name: String
)
