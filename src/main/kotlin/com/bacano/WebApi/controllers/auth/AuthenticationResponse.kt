package com.bacano.WebApi.controllers.auth

import com.bacano.WebApi.controllers.usuario.UsuarioResponse

data class AuthenticationResponse(
    val accessToken: String,
    val usuario: UsuarioResponse
)
