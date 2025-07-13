package com.bacano.WebApi.controllers.auth

import com.bacano.WebApi.services.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse =
        authService.authentication(authRequest)
}