package com.bacano.WebApi.controllers.meta

data class FiltroMetasLogradasPorUsuarioRequest(
    val estanLogradas: Boolean,
    val usuarioId: Long
)
