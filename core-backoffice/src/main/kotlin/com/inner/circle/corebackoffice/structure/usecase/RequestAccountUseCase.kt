package com.inner.circle.corebackoffice.structure.usecase

fun interface RequestAccountUseCase {
    data class Request(
        val name: String
    )

    fun account(request: Request)
}
