package com.bacano.WebApi.controllers

import com.bacano.WebApi.dto.AutorizacionResponseDto
import com.bacano.WebApi.dto.LoginRequestDto
import com.bacano.WebApi.model.Usuarios
import com.bacano.WebApi.services.UsuariosService
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
class UsuariosController(
    private val usuariosService: UsuariosService
) {
    @GetMapping
    fun getUsuarios(): List<Usuarios> = usuariosService.getAll()

    @PostMapping("/login")
    fun login(@RequestBody response: LoginRequestDto): ResponseEntity<Any> {
        val user = usuariosService.login(response.email, response.contrasena)
        return if (user == null) {
            ResponseEntity.status(401).body("Email o contraseña incorrectos")
        } else {
            val authRequest = AutorizacionResponseDto(
                token = UUID.randomUUID().toString(),
                usuario = user
            )
            ResponseEntity.ok(authRequest)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody usuario: Usuarios): ResponseEntity<Any> {
        return if (usuariosService.checkEmailExistance(usuario.email)) {
            ResponseEntity.status(409).body("Este email ya está en uso")
        } else {
            usuariosService.register(usuario)
            ResponseEntity.ok("El usuario se ha registrado correctamente")
        }
    }
}