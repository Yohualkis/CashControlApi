package com.bacano.WebApi.controllers.usuario

import java.util.Date

data class UsuarioResponse(
    val usuarioId: Long,
    val nombre: String,
    val email: String,
    val fotoPath: String?,
    val fechaRegistro: Date
)
