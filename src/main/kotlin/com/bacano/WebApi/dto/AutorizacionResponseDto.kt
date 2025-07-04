package com.bacano.WebApi.dto

import com.bacano.WebApi.model.Usuarios

data class AutorizacionResponseDto(
    val token: String,
    val usuario: Usuarios
)