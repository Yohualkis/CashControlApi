package com.bacano.WebApi.services

import com.bacano.WebApi.model.Usuario
import com.bacano.WebApi.repositories.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val userRepo: UsuarioRepository,
) {
    fun getAll(): List<Usuario> = userRepo.findAll()
    fun register(usuario: Usuario): Usuario = userRepo.save(usuario)
    fun login(email: String, contrasena: String): Usuario? = userRepo.findByEmailAndContrasena(email, contrasena)
    fun checkEmailExistance(email: String): Boolean = userRepo.findByEmail(email)
}