package com.inner.circle.infra.repository.memorydatabase

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import java.time.Duration
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PaymentTokenMemoryRepository(
    val redisTemplate: StringRedisTemplate
) : PaymentTokenRepository {
    private val logger = LoggerFactory.getLogger(PaymentTokenMemoryRepository::class.java)

    override fun getPaymentDataFromToken(token: String): PaymentTokenEntity {
        val tokenData = redisTemplate.opsForHash<String, String>().entries(token)
        if (tokenData.isEmpty()) {
            throw PaymentJwtException.TokenNotFoundException()
        }
        return PaymentTokenEntity.fromToken(tokenData)
    }

    override fun savePaymentToken(
        paymentToken: PaymentTokenEntity,
        expiresAt: LocalDateTime
    ): PaymentTokenEntity {
        val tokenString = paymentToken.generatedToken
        val ttl = Duration.between(LocalDateTime.now(), expiresAt)

        redisTemplate.opsForHash<String, String>().put(tokenString, "token", tokenString)
        redisTemplate.opsForHash<String, String>().put(
            tokenString,
            "merchantId",
            paymentToken.merchantId
        )
        redisTemplate.opsForHash<String, String>().put(tokenString, "orderId", paymentToken.orderId)
        redisTemplate.opsForHash<String, String>().put(
            tokenString,
            "signature",
            paymentToken.signature
        )
        redisTemplate.expire(tokenString, ttl)

        return paymentToken
    }

    override fun removePaymentDataByToken(token: String) {
        try {
            logger.info("Token removed: $token")
            redisTemplate.opsForHash<String, String>().delete("tokens", token)
        } catch (ex: Exception) {
            logger.error("Failed to remove token: $token", ex)
        }
    }
}
