package com.bacano.WebApi.controllers.auth

data class AuthenticationRequest(
    val email: String,
    val password: String,
)