package com.inner.circle.api.controller.dto

import com.inner.circle.core.security.AccountDetails

data class UserInfoResponse(
    val id: String,
    val email: String
) {
    companion object {
        fun from(userInfo: AccountDetails): UserInfoResponse =
            UserInfoResponse(
                id = userInfo.id.toString(),
                email = userInfo.username
            )
    }
}
