package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.repository.UserCardRepository
import com.inner.circle.infra.repository.entity.UserCardEntity
import com.inner.circle.infra.structure.port.SaveUserCardPort

internal class SaveUserCardAdaptor(
    private val userCardRepository: UserCardRepository
) : SaveUserCardPort {
    override fun save(request: SaveUserCardPort.Request) {
        userCardRepository.save(
            UserCardEntity(
                null,
                request.userId,
                request.representativeYn,
                request.cardNumber,
                request.expirationPeriod,
                request.cvc
            )
        )
    }
}
