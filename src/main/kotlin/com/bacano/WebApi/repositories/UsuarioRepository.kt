package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByEmailAndContrasena(email: String, contrasena: String): Usuario?
    fun findByEmail(email: String): Boolean
}