package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByEmail(email: String): Usuario?
    fun findByUsuarioId(id: Long): Usuario?
}