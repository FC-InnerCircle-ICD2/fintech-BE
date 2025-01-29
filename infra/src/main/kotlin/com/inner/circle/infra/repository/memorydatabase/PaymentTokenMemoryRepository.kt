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

    override fun isExpiredByToken(
        merchantId: String,
        orderId: String
    ): Boolean {
        val key = "$merchantId:$orderId"
        val tokenString =
            redisTemplate.opsForValue()[key]
                ?: return true // 레코드가 없으면 true 반환
        val paymentToken = PaymentTokenEntity.fromToken(tokenString)
        if (paymentToken.expiresAt.isBefore(LocalDateTime.now())) {
            throw PaymentJwtException.TokenExpiredException()
        }
        return false
    }

    override fun savePaymentToken(paymentToken: PaymentTokenEntity): PaymentTokenEntity {
        val key = "${paymentToken.merchantId}:${paymentToken.orderId}"
        val tokenString = paymentToken.toString()
        val ttl = Duration.between(LocalDateTime.now(), paymentToken.expiresAt)
        redisTemplate.opsForValue().set(key, tokenString, ttl)
        return paymentToken
    }
}
