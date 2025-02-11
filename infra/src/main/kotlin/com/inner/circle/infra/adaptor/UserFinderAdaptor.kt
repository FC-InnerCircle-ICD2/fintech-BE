package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.UserFinderPort
import com.inner.circle.infra.repository.UserRepository
import com.inner.circle.infra.repository.entity.AccountEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserFinderAdaptor(
    private val userRepository: UserRepository
) : UserFinderPort {
    override fun findByIdOrNull(id: Long): AccountEntity? = userRepository.findByIdOrNull(id)
}
