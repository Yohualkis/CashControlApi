package com.bacano.WebApi.controllers.usuario

import java.util.Date

data class UsuarioRequest(
    val usuarioId: Long,
    val nombre: String,
    val email: String,
    val password: String,
    val fechaRegistro: Date,
)
