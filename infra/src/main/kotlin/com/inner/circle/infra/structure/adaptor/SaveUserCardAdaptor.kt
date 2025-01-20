package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.port.SaveUserCardPort
import com.inner.circle.infra.structure.repository.UserCardRepository
import com.inner.circle.infra.structure.repository.entity.UserCardEntity
import org.springframework.stereotype.Component

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
