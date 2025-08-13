package com.bacano.WebApi.controllers.meta

import java.util.*

data class MetaDetalleResponse (
    val metaDetalleId: Long,
    val metaId: Long,
    val montoAporte: Double,
    val fechaAporte: Date,
)
