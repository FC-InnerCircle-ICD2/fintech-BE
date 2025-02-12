package com.inner.circle.core.security

import com.inner.circle.infra.port.AccountFinderPort
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
            val accountInfo =
                accountFinderPort.findByIdOrNull(
                    id = it["userId"].toString().toLong()
                ) ?: throw RuntimeException("Invalid User !")

            UsernamePasswordAuthenticationToken(
                AccountDetails(
                    id = accountInfo.id,
                    userName = accountInfo.email,
                    userPassword = accountInfo.password
                ),
                null
            )
        } ?: throw RuntimeException("Invalid token !")

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

    companion object {
        private val logger = LoggerFactory.getLogger(UserValidationService::class.java)
    }
}
