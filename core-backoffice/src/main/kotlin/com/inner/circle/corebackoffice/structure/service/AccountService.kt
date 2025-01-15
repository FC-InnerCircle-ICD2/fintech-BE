package com.inner.circle.corebackoffice.structure.service

import com.inner.circle.corebackoffice.structure.domain.Account
import com.inner.circle.corebackoffice.structure.usecase.RequestAccountUseCase
import com.inner.circle.infrabackoffice.structure.port.SaveAccountPort
import org.springframework.stereotype.Service

@Service
internal class AccountService(
    private val saveAccountPort: SaveAccountPort
) : RequestAccountUseCase {
    override fun account(request: RequestAccountUseCase.Request) {
        val account = Account(request.name)
        saveAccountPort.save(
            SaveAccountPort.Request(
                account.name
            )
        )
    }
}
