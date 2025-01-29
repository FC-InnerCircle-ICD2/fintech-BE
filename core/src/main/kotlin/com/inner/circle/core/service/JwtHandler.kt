package com.inner.circle.core.service

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
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

    fun generateToken(
        paymentClaimDto: PaymentClaimDto,
        issuedAt: Date,
        expiresMinute: Int
    ): String {
        val paymentToken =
            paymentClaimDto.paymentToken ?: throw PaymentJwtException.TokenNotFoundException()
        val signature = generateSignatureWithPaymentToken(paymentToken)
        val expirationDate = Date(issuedAt.time + 1000 * 60 * expiresMinute)
        return Jwts
            .builder()
            .subject(paymentClaimDto.orderId)
            .claim("merchantId", paymentClaimDto.merchantId)
            .claim("orderName", paymentClaimDto.orderName)
            .claim("amount", paymentClaimDto.amount)
            .issuedAt(issuedAt)
            .expiration(expirationDate)
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
