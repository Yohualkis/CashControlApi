package com.bacano.WebApi.services

import com.bacano.WebApi.model.Usuario
import com.bacano.WebApi.repositories.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val userRepo: UsuarioRepository,
) {
    fun getAll(): List<Usuario> =
        userRepo.findAll()

    fun createUser(usuario: Usuario): Usuario? {
        val usuarioExistente = userRepo.findByEmail(usuario.email)

        return if (usuarioExistente == null) {
            userRepo.save(usuario)
        } else null
    }

    fun getUserById(id: Long) = userRepo.findByUsuarioId(id)

    fun verificarCredenciales(email: String, password: String): Usuario? {
        val user = userRepo.findByEmail(email)
            ?: return null
        val passwordMatch = password == user.password
        if(!passwordMatch)
            return null
        return user
    }

    fun deleteUserById(id: Long): Boolean {
        val userEncontrado = userRepo.findByUsuarioId(id)
            ?: return false
        userRepo.delete(userEncontrado)
        return true
    }
}