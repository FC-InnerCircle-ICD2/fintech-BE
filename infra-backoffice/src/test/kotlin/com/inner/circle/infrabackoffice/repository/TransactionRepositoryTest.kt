package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.config.JpaConfiguration
import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import com.inner.circle.infrabackoffice.repository.entity.TransactionStatus
import io.kotest.core.spec.style.ExpectSpec
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ContextConfiguration(classes = [TransactionRepositoryImpl::class, JpaConfiguration::class])
@ComponentScan(basePackages = ["com.inner.circle.infrabackoffice"])
class TransactionRepositoryTest(
    private var transactionRepository: TransactionRepository
) : ExpectSpec(
        {

            context("transaction 단 건 조회") {
                val paymentKey = UUID.randomUUID().toString()
                val transaction =
                    TransactionEntity(
                        id = null,
                        paymentKey = paymentKey,
                        amount = BigDecimal.valueOf(100.0),
                        status = TransactionStatus.APPROVED,
                        reason = "APPROVED",
                        requestedAt = LocalDateTime.now()
                    )
                val savedTransaction = transactionRepository.save(transaction)

                expect("조회된 transaction 리스트에 paymentKey로 저장된 transaction이 포함되어야 한다.") {
                    val actual = transactionRepository.findByPaymentKey(paymentKey)
                    val expectedTransaction = actual.find { it.id == savedTransaction.id }
                    assertNotNull(expectedTransaction)
                    assertEquals(0, expectedTransaction?.amount?.compareTo(transaction.amount))
                    assertEquals(transaction.paymentKey, expectedTransaction?.paymentKey)
                }

                expect("paymentKey로 저장된 transaction이 4개가 조회되어야 한다.") {
                    transactionRepository.save(
                        TransactionEntity(
                            id = null,
                            paymentKey = paymentKey,
                            amount = BigDecimal.valueOf(50.0),
                            status = TransactionStatus.CANCELED,
                            reason = "CANCELED",
                            requestedAt = LocalDateTime.now()
                        )
                    )

                    transactionRepository.save(
                        TransactionEntity(
                            id = null,
                            paymentKey = paymentKey,
                            amount = BigDecimal.valueOf(50.0),
                            status = TransactionStatus.CANCELED,
                            reason = "CANCELED",
                            requestedAt = LocalDateTime.now()
                        )
                    )

                    transactionRepository.save(
                        TransactionEntity(
                            id = null,
                            paymentKey = paymentKey,
                            amount = BigDecimal.valueOf(100.0),
                            status = TransactionStatus.APPROVED,
                            reason = "APPROVED",
                            requestedAt = LocalDateTime.now()
                        )
                    )

                    val actual = transactionRepository.findByPaymentKey(paymentKey)
                    assertEquals(4, actual.size)
                    actual.forEach { assertEquals(paymentKey, it.paymentKey) }
                }
            }
        }
    )
