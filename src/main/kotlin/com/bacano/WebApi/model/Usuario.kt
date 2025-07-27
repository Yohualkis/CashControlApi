package com.bacano.WebApi.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val usuarioId: Long = 0,
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val fotoPath: String? = null,
    val fechaRegistro: Date = Date(),
    val rol: Rol = Rol.USER
)

enum class Rol {
    USER, ADMIN
}