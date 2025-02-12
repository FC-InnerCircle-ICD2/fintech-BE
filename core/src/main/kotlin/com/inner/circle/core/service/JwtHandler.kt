package com.inner.circle.core.service

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class JwtHandler {
    private val logger = LoggerFactory.getLogger(JwtHandler::class.java)

    fun generateToken(
        paymentClaimDto: PaymentClaimDto,
        issuedAt: Date,
        keyString: String
    ): String {
        val signatureKey = getSignature(keyString)
        return Jwts
            .builder()
            .claim("merchantName", paymentClaimDto.merchantName)
            .claim("orderId", paymentClaimDto.orderId)
            .claim("orderName", paymentClaimDto.orderName)
            .claim("amount", paymentClaimDto.amount)
            .issuedAt(issuedAt)
            .signWith(signatureKey, Jwts.SIG.HS384)
            .compact()
    }

    fun validateToken(
        token: String,
        keyString: String
    ): Boolean =
        try {
            val signatureKey = getSignature(keyString)
            Jwts
                .parser()
                .verifyWith(signatureKey)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            when (e) {
                is io.jsonwebtoken.ExpiredJwtException -> {
                    logger.error("Token validation error: Token has expired", e)
                    throw PaymentJwtException.TokenExpiredException(cause = e)
                }

                is io.jsonwebtoken.MalformedJwtException -> {
                    logger.error("Token validation error: Invalid token", e)
                    throw PaymentJwtException.TokenInvalidException(cause = e)
                }

                else -> {
                    logger.error("Token validation error occurred.", e)
                    throw PaymentJwtException.TokenInvalidException(cause = e)
                }
            }
        }

    fun generateSignatureString(): String {
        val key =
            Jwts.SIG.HS384
                .key()
                .build()
        return Encoders.BASE64.encode(key.encoded)
    }

    private fun getSignature(signString: String): SecretKey =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(signString))
}
