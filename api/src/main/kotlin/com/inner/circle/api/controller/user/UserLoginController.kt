package com.inner.circle.api.controller.user

import com.inner.circle.api.controller.request.UserLoginRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserLoginController {

    @PostMapping("/login")
    fun userLogin(
        @RequestBody userLoginRequest: UserLoginRequest,
    ) {

    }
}

fun main(args: Array<String>) {
    val userRawPassword = "pay200-user"

    val bCrypt = BCryptPasswordEncoder()
    val encodePassword = bCrypt.encode("pay200-user")
    println(encodePassword)


    println(bCrypt.matches(userRawPassword, encodePassword))
    println("MATCH DATA CHECK ${bCrypt.encode("pay200-user")}")

    println(bCrypt.encode("pay200-user"))
    val test = "\$2a\$10\$3LUZbNN8PSXkuq1.zO1pEuIXkERIc/RwQbJSV/7EILrYzia7AlRC."
    println(bCrypt.matches("pay200-user", test))
    // $2a$10$y9u68rF3yR/c.hHzAL8YOO.eQ/mEQhZy58gAkUXppHOnwEJ9Qm/nG
    // $2a$10$S09UPOa5ZQh4n/Yb1PdRnuoJgWJ.f.Z20
}
