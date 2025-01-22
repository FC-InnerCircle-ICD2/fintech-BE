package com.inner.circle.infra.structure.repository

import com.inner.circle.exception.PaymentTokenException
import com.inner.circle.infra.structure.config.RedisTestConfiguration
import com.inner.circle.infra.structure.repository.entity.PaymentTokenEntity
import java.time.Duration
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.StringRedisTemplate


@SpringBootTest(classes = [PaymentTokenMemoryRepository::class])
@Import(RedisTestConfiguration::class)
class PaymentTokenMemoryRepositoryTest {

    @Autowired
    private lateinit var redisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var repository: PaymentTokenMemoryRepository

    @Test
    fun `getPaymentToken should return token when exists`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant1:order1"
        val tokenString = "merchant1,order1,token123,${LocalDateTime.now().plusHours(1)}"
        redisTemplate.opsForValue()[key] = tokenString

        val token = repository.getPaymentToken("merchant1", "order1")

        assertNotNull(token)
        assertEquals("merchant1", token.merchantId)
        assertEquals("order1", token.orderId)
        assertEquals("token123", token.generatedToken)
    }

    @Test
    fun `getPaymentToken should throw exception when token not found`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant2:order1"

        assertThrows<PaymentTokenException.TokenNotFoundException> {
            repository.getPaymentToken("merchant2", "order1")
        }
    }

    @Test
    fun `isExpiredByToken should return true when token not found`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant999:order123"

        val isExpired = repository.isExpiredByToken("merchant999", "order123")

        assertTrue(isExpired)
    }

    @Test
    fun `isExpiredByToken should throw exception when token is expired`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant1:order1"
        val tokenString = "merchant1,order1,token123,${LocalDateTime.now().minusHours(1)}"
        redisTemplate.opsForValue()[key] = tokenString

        assertThrows<PaymentTokenException.TokenExpiredException> {
            repository.isExpiredByToken("merchant1", "order1")
        }
    }

    @Test
    fun `isExpiredByToken should return false when token is not expired`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant1:order1"
        val tokenString = "merchant1,order1,token123,${LocalDateTime.now().plusHours(1)}"
        redisTemplate.opsForValue()[key] = tokenString

        val isExpired = repository.isExpiredByToken("merchant1", "order1")

        assertFalse(isExpired)
    }

    @Test
    fun `savePaymentToken should save token`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val paymentToken = PaymentTokenEntity(
            merchantId = "merchant1",
            orderId = "order1",
            generatedToken = "token123",
            expiresAt = LocalDateTime.now().plusHours(1)
        )
        val key = "${paymentToken.merchantId}:${paymentToken.orderId}"
        val tokenString = paymentToken.toString()
        val ttl = Duration.between(LocalDateTime.now(), paymentToken.expiresAt)

        repository.savePaymentToken(paymentToken)

        val savedTokenString = redisTemplate.opsForValue()[key]
        assertNotNull(savedTokenString)
        assertEquals(tokenString, savedTokenString)
    }
}