package com.inner.circle.infrabackoffice.structure.port

fun interface SaveAccountPort {
    data class Request(
        val name: String
    )

    fun save(request: Request)
}
