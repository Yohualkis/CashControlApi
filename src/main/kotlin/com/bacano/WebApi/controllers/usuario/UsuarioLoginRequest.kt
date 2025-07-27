package com.bacano.WebApi.controllers.usuario

data class UsuarioLoginRequest(
    val email: String,
    val password: String,
)
