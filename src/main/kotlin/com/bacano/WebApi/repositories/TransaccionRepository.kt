package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Transaccion
import org.springframework.data.jpa.repository.JpaRepository


interface TransaccionRepository: JpaRepository<Transaccion, Long> {
    fun findByCategoriaTipoCategoriaAndUsuarioUsuarioId(
        tipoCategoria: String,
        usuarioId: Long
    ): List<Transaccion>
}