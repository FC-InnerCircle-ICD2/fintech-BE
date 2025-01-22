package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.SaveUserCardPort
import com.inner.circle.infra.repository.UserCardRepository
import com.inner.circle.infra.repository.entity.UserCardEntity
import org.springframework.stereotype.Component

@Component
internal class SaveUserCardAdaptor(
    private val userCardRepository: UserCardRepository
) : SaveUserCardPort {
    override fun save(request: SaveUserCardPort.Request) {
        userCardRepository.save(
            UserCardEntity(
                0L,
                request.userId,
                request.representativeYn,
                request.cardNumber,
                request.expirationPeriod,
                request.cvc
            )
        )
    }
}
