package com.bacano.WebApi.controllers.transacciones

data class TransaccionRequest(
    val transaccionId: Long,
    val categoriaId: Long,
    val usuarioId: Long,
    val monto: Double,
    val usarSugerencia: Boolean,
    val descripcion: String,
)