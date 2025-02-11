package com.inner.circle.core.service

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.math.BigDecimal
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

fun main() {
//    val secret = "0123456789abcdef0123456789abcdef"
    val secretKey = Keys.hmacShaKeyFor("0123456789abcdef0123456789abcdef0123456789abcdef".toByteArray())
    val tokenTest =
        Jwts.builder()
            .claim("userId", "123456")  // 사용자 ID
            .claim("role", "ROLE_ADMIN")  // 역할 정보
            .issuedAt(Date.from(Instant.now()))
            .signWith(SignatureAlgorithm.HS256, secretKey)  // HMAC 서명
            .compact()

    println(tokenTest)
}


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
        signString: String
    ): Boolean =
        try {
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

    fun extractClaims(
        token: String,
        signString: String
    ): Claims =
        try {
            val decrypted =
                Jwts
                    .parser()
                    .decryptWith(
                        generateSignature(signString)
                    ).build()
            decrypted.parseSignedClaims(token).payload
        } catch (e: Exception) {
            when (e) {
                is io.jsonwebtoken.ExpiredJwtException -> {
                    logger.error("Token extraction error: Token has expired", e)
                    throw PaymentJwtException.TokenExpiredException(cause = e)
                }

                is io.jsonwebtoken.MalformedJwtException -> {
                    logger.error("Token extraction error: Invalid token", e)
                    throw PaymentJwtException.TokenInvalidException(cause = e)
                }

                else -> {
                    logger.error("Token extraction error occurred.", e)
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

    fun extractMerchantId(
        token: String,
        paymentToken: String
    ): String =
        try {
            extractClaims(token, paymentToken)["merchantId", String::class.java]
        } catch (e: Exception) {
            logger.error("Error extracting merchantId from token", e)
            throw PaymentJwtException.ClaimExtractionException("merchantId", cause = e)
        }

    fun extractOrderName(
        token: String,
        paymentToken: String
    ): String? =
        try {
            extractClaims(token, paymentToken)["orderName", String::class.java]
        } catch (e: Exception) {
            logger.error("Error extracting orderName from token", e)
            throw PaymentJwtException.ClaimExtractionException("orderName", cause = e)
        }

    fun extractAmount(
        token: String,
        paymentToken: String
    ): BigDecimal =
        try {
            extractClaims(token, paymentToken)["amount", BigDecimal::class.java]
        } catch (e: Exception) {
            logger.error("Error extracting amount from token", e)
            throw PaymentJwtException.ClaimExtractionException("amount", cause = e)
        }
}
