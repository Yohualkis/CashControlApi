package com.bacano.WebApi.controllers.transacciones

import java.util.*

data class TransaccionResponse(
    val transaccionId: Long,
    val categoriaId: Long,
    val usuarioId: Long,
    val monto: Double,
    val fechaTransaccion: Date,
    val usarSugerencia: Boolean,
    val descripcion: String,
)