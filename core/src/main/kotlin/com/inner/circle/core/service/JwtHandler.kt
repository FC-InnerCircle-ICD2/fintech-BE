package com.inner.circle.core.service

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import io.jsonwebtoken.Jwts
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
        issuedAt: Date
    ): String {
        // merchantId + "_" + orderId 조합
        val signString = "${paymentClaimDto.merchantId}_${paymentClaimDto.orderId}"
        val signature = generateSignature(signString)
        return Jwts
            .builder()
            .claim("merchantName", paymentClaimDto.merchantName)
            .claim("orderId", paymentClaimDto.orderId)
            .claim("orderName", paymentClaimDto.orderName)
            .claim("amount", paymentClaimDto.amount)
            .issuedAt(issuedAt)
            .signWith(signature)
            .compact()
    }

    fun validateToken(
        token: String,
        merchantId: String,
        orderId: String
    ): Boolean =
        try {
            val signString = "${merchantId}_$orderId"
            Jwts
                .parser()
                .verifyWith(
                    generateSignature(signString)
                ).build()
                .parseSignedContent(token)
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

    private fun generateSignature(signString: String): SecretKey? {
        val keyBytes = ByteArray(32) // 32바이트 크기의 바이트 배열 생성
        val signStringBytes = signString.toByteArray()

        System.arraycopy(signStringBytes, 0, keyBytes, 0, signStringBytes.size.coerceAtMost(32))

        return Keys.hmacShaKeyFor(keyBytes)
    }
}
