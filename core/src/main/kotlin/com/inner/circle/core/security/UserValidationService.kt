package com.inner.circle.core.security

import com.inner.circle.infra.port.UserFinderPort
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UserValidationService(
    @Value("\${jwt.secret}") private val secret: String,
    private val userFinderPort: UserFinderPort
) : UserValidation {
    override fun validateUserOrThrow(token: String) {
        decodeUserAuthorizationToken(
            token = token,
            secretKey = secret
        )?.let {
            userFinderPort.findByIdOrNull(
                id = it["userId"].toString().toLong()
            )
        } ?: throw RuntimeException("Invalid token")
    }

    fun decodeUserAuthorizationToken(
        token: String,
        secretKey: String
    ): Claims? =
        try {
            Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            println("Invalid JWT: ${e.message}")
            null
        }
}
