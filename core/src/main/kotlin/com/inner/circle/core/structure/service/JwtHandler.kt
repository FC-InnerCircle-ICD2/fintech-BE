package com.inner.circle.core.structure.service

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.Password
import java.math.BigDecimal
import java.util.Date
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class JwtHandler {
    private val logger = LoggerFactory.getLogger(JwtHandler::class.java)
    private val expirationTime = 1000 * 60 * 10 // 10분

    fun generateToken(
        paymentClaimDto: PaymentClaimDto,
        paymentToken: String
    ): String {
        val signature = generateSignatureWithPaymentToken(paymentToken)
        return Jwts
            .builder()
            .subject(paymentClaimDto.orderId)
            .claim("merchantId", paymentClaimDto.merchantId)
            .claim("orderName", paymentClaimDto.orderName)
            .claim("amount", paymentClaimDto.amount)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(signature)
            .compact()
    }

    fun validateToken(
        token: String,
        paymentToken: String
    ): Boolean =
        try {
            Jwts
                .parser()
                .verifyWith(
                    generateSignatureWithPaymentToken(paymentToken)
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
        paymentToken: String
    ): Claims =
        try {
            val decrypted =
                Jwts
                    .parser()
                    .decryptWith(
                        generateSignatureWithPaymentToken(paymentToken)
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

    private fun generateSignatureWithPaymentToken(paymentToken: String): Password? =
        Keys.password(paymentToken.toCharArray())

    fun extractMerchantId(
        token: String,
        paymentToken: String
    ): String =
        try {
            extractClaims(token, paymentToken).get("merchantId", String::class.java)
        } catch (e: Exception) {
            logger.error("Error extracting merchantId from token", e)
            throw PaymentJwtException.ClaimExtractionException("merchantId", cause = e)
        }

    fun extractOrderName(
        token: String,
        paymentToken: String
    ): String? =
        try {
            extractClaims(token, paymentToken).get("orderName", String::class.java)
        } catch (e: Exception) {
            logger.error("Error extracting orderName from token", e)
            throw PaymentJwtException.ClaimExtractionException("orderName", cause = e)
        }

    fun extractAmount(
        token: String,
        paymentToken: String
    ): BigDecimal =
        try {
            extractClaims(token, paymentToken).get("amount", BigDecimal::class.java)
        } catch (e: Exception) {
            logger.error("Error extracting amount from token", e)
            throw PaymentJwtException.ClaimExtractionException("amount", cause = e)
        }
}
