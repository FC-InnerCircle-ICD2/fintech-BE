package com.inner.circle.apibackoffice.structure

import com.inner.circle.apibackoffice.structure.dto.AccountRequest
import com.inner.circle.corebackoffice.structure.usecase.RequestAccountUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountUseCase: RequestAccountUseCase
) {
    @PostMapping("")
    fun account(
        @RequestBody request: AccountRequest
    ): String {
        accountUseCase.account(
            RequestAccountUseCase.Request(
                request.name
            )
        )
        return request.name
    }
}
