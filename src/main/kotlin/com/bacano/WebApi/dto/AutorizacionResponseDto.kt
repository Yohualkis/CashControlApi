package com.bacano.WebApi.dto

import java.util.*

data class AutorizacionResponseDto(
    val token: String,
    val usuarioId: Long = 0,
    val nombre: String = "",
    val email: String = "",
    val fotoPath: String? = "",
    val fechaRegistro: Date,
)