package com.bacano.WebApi.services

import com.bacano.WebApi.model.Usuarios
import com.bacano.WebApi.repositories.UsuariosRepository
import org.springframework.stereotype.Service

@Service
class UsuariosService(
    private val userRepo: UsuariosRepository,
) {
    fun getAll(): List<Usuarios> = userRepo.findAll()
    fun register(usuario: Usuarios): Usuarios = userRepo.save(usuario)
    fun login(email: String, contrasena: String): Usuarios? = userRepo.findByEmailAndContrasena(email, contrasena)
    fun checkEmailExistance(email: String): Boolean = userRepo.findByEmail(email)
}