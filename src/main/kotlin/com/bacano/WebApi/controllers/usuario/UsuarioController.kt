package com.bacano.WebApi.controllers.usuario

import com.bacano.WebApi.model.Rol
import com.bacano.WebApi.model.Usuario
import com.bacano.WebApi.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/usuarios/")
class UsuarioController(
    private val usuarioService: UsuarioService,
) {
    @PostMapping("login")
    fun login(@RequestBody loginRequest: UsuarioLoginRequest): UsuarioResponse {
        return usuarioService.verificarCredenciales(
            email = loginRequest.email,
            password = loginRequest.password
        )
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email o contraseña incorrectos")
    }

    @PostMapping("register")
    fun crear(@RequestBody userRequest: UsuarioRequest): UsuarioResponse =
        usuarioService.createUser(
            usuario = userRequest.toModel()
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.CONFLICT, "Este email ya está ocupado")

    @GetMapping
    fun getUsuarios(): List<UsuarioResponse> =
        usuarioService.getAll().map {
            it.toResponse()
        }

    @GetMapping("{id}")
    fun getUsuarioPorId(@PathVariable id: Long): UsuarioResponse =
        usuarioService.getUserById(id)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario")

    @DeleteMapping("{id}")
    fun deleteUsuarioPorId(@PathVariable id: Long): ResponseEntity<Boolean> {
        val usuarioEliminado = usuarioService.deleteUserById(id)
        return if (usuarioEliminado)
            ResponseEntity.noContent().build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar un usuario que no existe")
    }

    private fun UsuarioRequest.toModel(): Usuario =
        Usuario(
            usuarioId = this.usuarioId,
            nombre = this.nombre,
            email = this.email,
            password = this.password,
            fotoPath = null,
            fechaRegistro = this.fechaRegistro,
            rol = Rol.USER
        )

    private fun Usuario.toResponse(): UsuarioResponse =
        UsuarioResponse(
            usuarioId = this.usuarioId,
            nombre = this.nombre,
            email = this.email,
            fotoPath = this.fotoPath,
            fechaRegistro = this.fechaRegistro
        )
}