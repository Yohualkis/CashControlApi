package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Usuarios
import org.springframework.data.jpa.repository.JpaRepository

interface UsuariosRepository: JpaRepository<Usuarios, Long> {
    fun findByEmailAndContrasena(email: String, contrasena: String): Usuarios?
    fun findByEmail(email: String): Boolean
}