package com.inner.circle.corebackoffice.service

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infrabackoffice.adaptor.dto.PaymentClaimDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.MacAlgorithm
import java.util.Date
import javax.crypto.SecretKey
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtHandler(
    @Value("\${jwt.secret}") private val secret: String
) {
    private val logger = LoggerFactory.getLogger(JwtHandler::class.java)

    fun <T> generateTokenBy(
        expireTargetDate: Date = Date(System.currentTimeMillis() + MILLI_SECONDS_IN_THREE_HOUR),
        issuedAt: Date = Date(),
        signAlgorithm: MacAlgorithm = Jwts.SIG.HS256,
        keyString: String = secret,
        claimTarget: T
    ): String =
        Jwts
            .builder()
            .claim(CLAIM_DATA_PREFIX, claimTarget)
            .issuedAt(issuedAt)
            .expiration(expireTargetDate)
            .signWith(getSignature(signString = keyString), signAlgorithm)
            .compact()

    fun getAuthorizationTokenClaimsOrNull(
        token: String,
        secretKey: String
    ): Claims? =
        runCatching {
            Jwts
                .parser()
                .verifyWith(getSignature(signString = secretKey))
                .build()
                .parseSignedClaims(token)
                .payload
        }.onFailure { exception ->
            when (exception) {
                is io.jsonwebtoken.ExpiredJwtException -> {
                    logger.info("Token validation error: Token has expired", exception)
                    throw PaymentJwtException.TokenExpiredException(cause = exception)
                }

                is io.jsonwebtoken.MalformedJwtException -> {
                    logger.error("Token validation error: Invalid token", exception)
                    throw PaymentJwtException.TokenInvalidException(cause = exception)
                }

                is io.jsonwebtoken.security.SignatureException -> {
                    logger.error("Token validation error: Invalid signature", exception)
                    throw PaymentJwtException.TokenInvalidException(cause = exception)
                }

                else -> {
                    logger.error("Token validation error occurred.", exception)
                    throw PaymentJwtException.TokenInvalidException(cause = exception)
                }
            }
        }.getOrElse { null }

    fun generateToken(
        paymentClaimDto: PaymentClaimDto,
        issuedAt: Date,
        keyString: String
    ): String {
        val signatureKey = getSignature(keyString)
        return Jwts
            .builder()
            .claim("merchantId", paymentClaimDto.merchantId)
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
                    logger.info("Token validation error: Token has expired", e)
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

    companion object {
        private const val MILLI_SECONDS_IN_THREE_HOUR = 3 * 60 * 60 * 1000
        private const val CLAIM_DATA_PREFIX = "data"
    }
}
