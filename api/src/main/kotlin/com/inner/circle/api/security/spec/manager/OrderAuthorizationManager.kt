package com.inner.circle.api.security.spec.manager

import jakarta.servlet.http.HttpServletRequest
import java.util.function.Supplier
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.util.AntPathMatcher

/**
 * @author Theo
 * @since 2025/02/15
 */
class OrderAuthorizationManager(
    // security 에서 DB 질의 할 수 있게끔 adapter를 만들기
    private val orderInternalAdapter: OrderInternalAdapter
) : AuthorizationManager<HttpServletRequest> {
    override fun check(
        authentication: Supplier<Authentication>,
        request: HttpServletRequest
    ): AuthorizationDecision {
        val orderId =
            request.pathVariable(
                pattern = "/api/v1/p/orders/{orderId}",
                name = "orderId"
            )

        val order = orderInternalAdapter.getByOrderId(orderId)
        /**
         * 1. 프로젝트에 맞게끔 authentication 에서 값 꺼내와 비교
         * 2. 현실적으로 봤을 때 여기서 굳이 Authentication 을 raw하게 직접 사용 할 필요는 없음.
         *    그렇기에 더 앞단에서 우리가 만든 커스텀 Holder에 값을 넣어주는 방법
         *    AccountHolder.set(CustomerAccountHolderValue(....))
         *    val account = AccountHolder.get()
         */
        order.createdAccountId == authentication.get().name // 프로젝트에 맞게끔 맞추고,
        order.createdAccountId == AccountHolder.get().accountId

        // 권한 없으면 exception
        return AuthorizationDecision(true)

        return AuthorizationDecision(order.createdAccountId == authentication.get().name)
    }

    private fun HttpServletRequest.pathVariable(
        pattern: String,
        name: String
    ): String =
        AntPathMatcher().extractUriTemplateVariables(
            pattern,
            requestURI
        )[name] as String
}
