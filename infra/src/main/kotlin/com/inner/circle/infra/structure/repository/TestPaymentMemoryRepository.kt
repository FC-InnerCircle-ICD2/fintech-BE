package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentEntity
import java.util.UUID
import org.springframework.stereotype.Repository

internal class TestPaymentMemoryRepository : TestPaymentRepository {
    private val storage: MutableMap<UUID, PaymentEntity> = mutableMapOf()

    override fun save(entity: PaymentEntity): PaymentEntity? {
//        val savedEntity: PaymentEntity =
//            when (val id: UUID? = entity.id) {
//                null ->
//                    entity.run {
//                        val newId: UUID = UUID.randomUUID()
//                        PaymentEntity
//                            .createWithId(
//                                entity.userName,
//                                entity.amount,
//                                entity.requestAt,
//                                newId
//                            ).also { storage[newId] = it }
//                    }
//                else -> entity.update(entity).also { storage[id] = it }
//            }
//        return savedEntity
        return null
    }
}
