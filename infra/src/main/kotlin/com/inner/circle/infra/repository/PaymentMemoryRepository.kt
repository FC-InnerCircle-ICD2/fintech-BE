package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
internal class PaymentMemoryRepository : PaymentRepository {
    private val storage: MutableMap<UUID, com.inner.circle.infra.repository.entity.PaymentEntity> = mutableMapOf()

    override fun save(entity: com.inner.circle.infra.repository.entity.PaymentEntity): com.inner.circle.infra.repository.entity.PaymentEntity? {
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
