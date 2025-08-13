package com.bacano.WebApi.controllers.meta

import java.util.*

data class MetaDetalleRequest(
    val metaDetalleId: Long,
    val metaId: Long,
    val montoAporte: Double,
    val fechaAporte: Date,
)
