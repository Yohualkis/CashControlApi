package com.bacano.WebApi.controllers.meta

data class MetaResponse(
    val metaId: Long,
    val usuarioId: Long,
    val descripcion: String,
    val fotoPath: String?,
    val montoMeta: Double,
    val estaLograda: Boolean
)
