package com.bacano.WebApi.controllers.categoria

data class CategoriaRequest(
    val categoriaId: Long,
    val tipoCategoria: String,
    val descripcion: String,
)
