package com.inner.circle.infrabackoffice.structure.adaptor

import com.inner.circle.infrabackoffice.structure.port.SaveAccountPort
import com.inner.circle.infrabackoffice.structure.repository.AccountRepository
import com.inner.circle.infrabackoffice.structure.repository.entity.AccountEntity
import org.springframework.stereotype.Component

@Component
internal class AccountAdaptor(
    private val repository: AccountRepository
) : SaveAccountPort {
    override fun save(request: SaveAccountPort.Request) {
        repository.save(
            AccountEntity(name = request.name)
        )
    }
}
