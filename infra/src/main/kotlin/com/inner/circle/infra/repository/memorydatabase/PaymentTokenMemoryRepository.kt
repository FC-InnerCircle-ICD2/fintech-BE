package com.inner.circle.infra.repository.memorydatabase

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import java.time.Duration
import java.time.LocalDateTime
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PaymentTokenMemoryRepository(
    val redisTemplate: StringRedisTemplate
) : PaymentTokenRepository {
    override fun getPaymentToken(
        merchantId: String,
        orderId: String
    ): PaymentTokenEntity {
        val key = "$merchantId:$orderId"
        val tokenString =
            redisTemplate.opsForValue()[key]
                ?: throw PaymentJwtException.TokenNotFoundException()
        return PaymentTokenEntity.fromToken(tokenString)
    }

    override fun savePaymentToken(
        paymentToken: PaymentTokenEntity,
        expiresAt: LocalDateTime
    ): PaymentTokenEntity {
        val key = "${paymentToken.merchantId}:${paymentToken.orderId}"
        val tokenString = paymentToken.toString()
        val ttl = Duration.between(LocalDateTime.now(), expiresAt)
        redisTemplate.opsForValue().set(key, tokenString, ttl)
        return paymentToken
    }
}
