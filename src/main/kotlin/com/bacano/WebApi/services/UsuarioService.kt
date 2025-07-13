package com.bacano.WebApi.services

import com.bacano.WebApi.model.Usuario
import com.bacano.WebApi.repositories.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val userRepo: UsuarioRepository,
    private val encoder: PasswordEncoder,
) {
    fun getAll(): List<Usuario> =
        userRepo.findAll()

    fun createUser(usuario: Usuario): Usuario? {
        val usuarioExistente = userRepo.findByEmail(usuario.email)

        return if (usuarioExistente == null) {
            val userConPasswordHasheada = usuario.copy(password = encoder.encode(usuario.password))
            userRepo.save(userConPasswordHasheada)
        } else null
    }

    fun getUserById(id: Long) = userRepo.findByUsuarioId(id)
    fun getUserByEmail(email: String) = userRepo.findByEmail(email)

    fun deleteUserById(id: Long): Boolean {
        val userEncontrado = userRepo.findByUsuarioId(id)
            ?: return false
        userRepo.delete(userEncontrado)
        return true
    }
}