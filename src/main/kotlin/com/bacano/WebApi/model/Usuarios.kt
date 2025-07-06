package com.bacano.WebApi.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "usuarios")
data class Usuarios(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val usuarioId: Long = 0,
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val fotoPath: String? = "",
    val fechaRegistro: Date = Date(),
)