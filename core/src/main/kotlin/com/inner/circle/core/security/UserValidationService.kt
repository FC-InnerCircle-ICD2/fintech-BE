package com.inner.circle.core.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UserValidationService(
    @Value("\${jwt.secret}") private val secret: String,
): UserValidation {
    override fun validateUserOrThrow(token: String) {
        val test = decodeUserAuthorizationToken(
            token = token,
            secretKey = secret,
        )

        println(test?.issuedAt)
        println(test?.size)
        println(test?.id)

        TODO("Not yet implemented")
    }

    fun decodeUserAuthorizationToken(token: String, secretKey: String): Claims? = try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            println("Invalid JWT: ${e.message}")
            null
        }
}

