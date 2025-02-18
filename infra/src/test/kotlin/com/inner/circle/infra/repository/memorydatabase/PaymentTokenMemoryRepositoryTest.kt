package com.inner.circle.infra.repository.memorydatabase

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.config.TestRedisConfiguration
import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import java.time.LocalDateTime
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
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
                merchantId = 123L,
                orderId = "order1",
                generatedToken = "token123",
                "test"
            )
        val key = "token123"

        repository.savePaymentToken(paymentToken, LocalDateTime.now().plusMinutes(5))

        val savedToken = redisTemplate.opsForHash<String, String>().entries("token123")
        assertNotNull(savedToken)
        Assertions.assertThat(savedToken["token"]).isEqualTo("token123")
        Assertions.assertThat(savedToken["merchantId"]).isEqualTo("123")
        Assertions.assertThat(savedToken["orderId"]).isEqualTo("order1")
        Assertions.assertThat(savedToken["signature"]).isEqualTo("test")
    }

    @DisplayName("토큰을 찾지 못하면 예외를 던진다.")
    @Test
    fun `fail_getPaymentToken should throw exception when token not found`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val key = "testToken"

        assertThrows<PaymentJwtException.TokenNotFoundException> {
            repository.getPaymentDataFromToken(key)
        }
    }

    @DisplayName("TTL 만료된 토큰 동작 테스트 - 토큰이 확인되지 않으므로 예외를 반환한다.")
    @Test
    fun `fail_isExpiredByToken should throw exception when token is expired`() {
        repository = PaymentTokenMemoryRepository(redisTemplate)
        val paymentToken =
            PaymentTokenEntity(
                merchantId = 12310L,
                orderId = "order10",
                generatedToken = "token123",
                "test"
            )

        repository.savePaymentToken(paymentToken, LocalDateTime.now().plusSeconds(1))

        Thread.sleep(1500)

        assertThrows<PaymentJwtException.TokenNotFoundException> {
            repository.getPaymentDataFromToken("token123")
        }
    }
}
