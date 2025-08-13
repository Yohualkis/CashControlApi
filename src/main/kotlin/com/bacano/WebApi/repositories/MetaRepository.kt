package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Meta
import org.springframework.data.jpa.repository.JpaRepository

interface MetaRepository : JpaRepository<Meta, Long> {
    fun findAllByUsuarioUsuarioId(
        usuarioId: Long,
    ): List<Meta>

    fun findAllByEstaLogradaAndUsuarioUsuarioId(
        estaLograda: Boolean,
        usuarioId: Long
    ): List<Meta>
}