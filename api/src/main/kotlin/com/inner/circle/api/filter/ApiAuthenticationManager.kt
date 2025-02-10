package com.inner.circle.api.filter

import com.inner.circle.api.filter.type.AuthenticationType
import com.inner.circle.exception.AppException
import com.inner.circle.exception.HttpStatus

interface AuthenticationInterface {
    fun validateOrThrow(authHeader: String?)
}

class ValidationFactory(
    private val authType: AuthenticationType,
) {
    fun getAuthentication(): AuthenticationInterface =
        when(authType) {
            AuthenticationType.USER -> MerchantAuthentication()
            AuthenticationType.MERCHANT -> UserAuthentication()
            AuthenticationType.SSE -> throw IllegalArgumentException("Unsupported authentication type")
        }
}

class MerchantAuthentication: AuthenticationInterface {
    // TODO - 상점 User 인증 구현
    override fun validateOrThrow(authHeader: String?) {
        if (authHeader == null) {
            throw AppException(HttpStatus.UNAUTHORIZED, "Unauthorized: Basic Auth required")
        }
    }
}


class UserAuthentication: AuthenticationInterface {
    // TODO - User 회원 인증 구현 !
    override fun validateOrThrow(authHeader: String?) {

    }
}
