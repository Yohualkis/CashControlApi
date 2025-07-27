package com.bacano.WebApi.controllers.categoria

data class CategoriaRequest(
    val categoriaId: Long,
    val usuarioId: Long,
    val tipoCategoria: String,
    val descripcion: String,
)
