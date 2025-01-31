package com.inner.circle.infra.repository.memorydatabase

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.config.TestRedisConfiguration
import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.StringRedisTemplate

@Import(TestRedisConfiguration::class)
@SpringBootTest(classes = [PaymentTokenMemoryRepository::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentTokenMemoryRepositoryTest {
    @Autowired
    private lateinit var redisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var repository: PaymentTokenMemoryRepository

    @DisplayName("토큰 저장 동작 확인")
    @Test
    fun `savePaymentToken should save token`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val paymentToken =
            PaymentTokenEntity(
                merchantId = "merchant1",
                orderId = "order1",
                generatedToken = "token123",
                expiresAt = LocalDateTime.now().plusMinutes(5)
            )
        val key = "${paymentToken.merchantId}:${paymentToken.orderId}"
        val tokenString = paymentToken.toString()

        repository.savePaymentToken(paymentToken)

        val savedTokenString = redisTemplate.opsForValue()[key]
        assertNotNull(savedTokenString)
        assertEquals(tokenString, savedTokenString)
    }

    @DisplayName("토큰이 존재하면 해당 토큰 문자열을 확인할 수 있다.")
    @Test
    fun `getPaymentToken should return token when exists`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant1:order1"
        val tokenString = "merchant1,order1,token123,${LocalDateTime.now().plusMinutes(5)}"
        redisTemplate.opsForValue()[key] = tokenString

        val token = repository.getPaymentToken("merchant1", "order1")

        assertNotNull(token)
        assertEquals("merchant1", token.merchantId)
        assertEquals("order1", token.orderId)
        assertEquals("token123", token.generatedToken)
    }

    @DisplayName("토큰을 찾지 못하면 예외를 던진다.")
    @Test
    fun `fail_getPaymentToken should throw exception when token not found`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant2:order1"

        assertThrows<PaymentJwtException.TokenNotFoundException> {
            repository.getPaymentToken("merchant2", "order1")
        }
    }

    @DisplayName("만료된 토큰을 조회 시 예외를 던진다.")
    @Test
    fun `fail_isExpiredByToken should return true when token not found`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant999:order123"
        val tokenString = "merchant999,order123,token123,${LocalDateTime.now().plusNanos(1)}"
        redisTemplate.opsForValue()[key] = tokenString

        val expiredByToken = repository.isExpiredByToken("merchant999", "order123")

        assertTrue(expiredByToken)
    }

    @DisplayName("TTL 만료된 토큰 동작 테스트 - 토큰이 확인되지 않으므로 예외를 반환한다.")
    @Test
    fun `fail_isExpiredByToken should throw exception when token is expired`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val paymentToken =
            PaymentTokenEntity(
                merchantId = "merchant10",
                orderId = "order10",
                generatedToken = "token123",
                expiresAt = LocalDateTime.now().plusSeconds(1)
            )

        repository.savePaymentToken(paymentToken)

        Thread.sleep(1500)

        assertThrows<PaymentJwtException.TokenNotFoundException> {
            repository.getPaymentToken("merchant10", "order10")
        }
    }

    @DisplayName("토큰을 조회할 때 만료되지 않았으면 false를 반환한다.")
    @Test
    fun `isExpiredByToken should return false when token is not expired`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "merchant1:order1"
        val tokenString = "merchant1,order1,token123,${LocalDateTime.now().plusMinutes(5)}"
        redisTemplate.opsForValue()[key] = tokenString

        val isExpired = repository.isExpiredByToken("merchant1", "order1")

        assertFalse(isExpired)
    }
}
