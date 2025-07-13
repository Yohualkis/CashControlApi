package com.bacano.WebApi.controllers.categoria

data class CategoriaResponse(
    val categoriaId: Long,
    val tipoCategoria: String,
    val descripcion: String,
    val nombreUsuario: String
)
