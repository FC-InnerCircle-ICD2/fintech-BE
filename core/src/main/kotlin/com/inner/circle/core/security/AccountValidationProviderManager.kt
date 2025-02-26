package com.inner.circle.core.security

import com.inner.circle.core.service.JwtHandler
import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.port.AccountFinderPort
import com.inner.circle.infra.repository.entity.AccountEntity
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class AccountValidationProviderManager(
    @Value("\${jwt.secret}") private val secret: String,
    private val accountFinderPort: AccountFinderPort,
    private val jwtHandler: JwtHandler
) : AccountValidationProvider {
    override fun getUserValidAuthenticationOrThrow(token: String): Authentication =
        jwtHandler
            .getAuthorizationTokenClaimsOrNull(
                token = token,
                secretKey = secret
            )?.let {
                val accountInfo =
                    accountFinderPort
                        .findByIdOrNull(
                            id = it.getAccountIdFromClaims()
                        )?.toUserDetails()
                        ?: throw UserAuthenticationException.UserNotFoundException()

                UsernamePasswordAuthenticationToken(
                    accountInfo,
                    null,
                    mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
                )
            } ?: throw UserAuthenticationException.UnauthorizedException()

    private fun AccountEntity.toUserDetails(): AccountDetails =
        AccountDetails(
            id = this.id,
            userName = this.email,
            userPassword = this.password
        )

    private fun Claims.getAccountIdFromClaims() =
        (this["data"] as Map<*, *>)["id"].toString().toLong()
}
