package com.inner.circle.core.security

import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.port.AccountFinderPort
import com.inner.circle.infra.repository.entity.AccountEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class UserValidationService(
    @Value("\${jwt.secret}") private val secret: String,
    private val accountFinderPort: AccountFinderPort
) : UserValidation {
    override fun getUserValidAuthenticationOrThrow(token: String): Authentication =
        getAuthorizationTokenClaimsOrNull(
            token = token,
            secretKey = secret
        )?.let {
            val accountInfo = accountFinderPort.findByIdOrNull(
                id = it["userId"].toString().toLong()
            )?.toUserDetails() ?: throw UserAuthenticationException.UserNotFoundException()

            UsernamePasswordAuthenticationToken(
                accountInfo,
            null
            )
        } ?: throw UserAuthenticationException.UnauthorizedException(message = "Invalid Token")

    fun getAuthorizationTokenClaimsOrNull(
        token: String,
        secretKey: String
    ): Claims? =
        runCatching {
            Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseSignedClaims(token)
                .payload
        }.onFailure {
            logger.error("Invalid token", it)
        }.getOrElse { null }

    private fun AccountEntity.toUserDetails(): AccountDetails =
        AccountDetails(
            id = this.id,
            userName = this.email,
            userPassword = this.password,
        )

    companion object {
        private val logger = LoggerFactory.getLogger(UserValidationService::class.java)
    }
}
