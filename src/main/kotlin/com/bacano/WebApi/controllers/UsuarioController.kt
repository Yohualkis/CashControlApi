package com.bacano.WebApi.controllers

import com.bacano.WebApi.dto.AutorizacionResponseDto
import com.bacano.WebApi.dto.LoginRequestDto
import com.bacano.WebApi.dto.UsuarioDto
import com.bacano.WebApi.model.Usuario
import com.bacano.WebApi.services.UsuarioService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "Autorización", description = "Este endpoint solo sera para loggear y registrar usuarios")
@RestController
@RequestMapping("api/autorizacion")
class UsuarioController(
    private val usuarioService: UsuarioService
) {
    @GetMapping
    fun getUsuarios(): List<Usuario> = usuarioService.getAll()

    @PostMapping("/login")
    fun login(@RequestBody response: LoginRequestDto): ResponseEntity<Any> {
        val user = usuarioService.login(response.email, response.contrasena)
        return if (user == null) {
            ResponseEntity.status(401).body("Email o contraseña incorrectos")
        } else {
            val userDto = UsuarioDto(
                usuarioId = user.usuarioId,
                nombre = user.nombre.lowercase(),
                email = user.email.lowercase(),
                fotoPath = user.fotoPath?.lowercase(),
                fechaRegistro = user.fechaRegistro,
            )
            val authRequest = AutorizacionResponseDto(
                token = UUID.randomUUID().toString(),
                usuario = userDto
            )
            ResponseEntity.ok(authRequest)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        return if (usuarioService.checkEmailExistance(usuario.email)) {
            ResponseEntity.status(409).body("Este email ya está en uso")
        } else {
            usuarioService.register(usuario)
            ResponseEntity.ok("El usuario se ha registrado correctamente")
        }
    }
}