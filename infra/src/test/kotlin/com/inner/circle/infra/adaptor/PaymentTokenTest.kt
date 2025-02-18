package com.inner.circle.infra.adaptor

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PaymentTokenTest {
    private lateinit var initialHash: String
    private val logger: Logger = LoggerFactory.getLogger(PaymentTokenTest::class.java)

    @BeforeEach
    fun setUp() {
        val target =
            "eyJhbGciOiJIUzM4NCJ9.eyJtZXJjaGFudE5hbWUiOiJwYXkyMDAiLCJvcmRlcklkIjoiMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxIiwib3JkZXJOYW1lIjoiMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxIiwiYW1vdW50IjoxMCwiaWF0IjoxNzM5ODA2MzQ2fQ.G-MJad-bAIY9t7CJIy2MK7-TEKRs6QCzkneuc-YV-QSbqEug_6dqSMJl2XPTuDlE"
        initialHash = Hashing.murmur3_128().hashString(target, StandardCharsets.UTF_8).toString()
    }

    @RepeatedTest(100)
    fun hashingTest() {
        val target =
            "eyJhbGciOiJIUzM4NCJ9.eyJtZXJjaGFudE5hbWUiOiJwYXkyMDAiLCJvcmRlcklkIjoiMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxIiwib3JkZXJOYW1lIjoiMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxIiwiYW1vdW50IjoxMCwiaWF0IjoxNzM5ODA2MzQ2fQ.G-MJad-bAIY9t7CJIy2MK7-TEKRs6QCzkneuc-YV-QSbqEug_6dqSMJl2XPTuDlE"
        val hash = Hashing.murmur3_128().hashString(target, StandardCharsets.UTF_8).toString()

        // 해시 값이 초기 해시 값과 일치하는지 확인
        logger.info("hash: $hash")
        assertEquals(initialHash, hash)
        Assertions.assertThat(hash).hasSize(32)
    }
}
