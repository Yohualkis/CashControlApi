package com.bacano.WebApi.model

import jakarta.persistence.*

@Entity
@Table(name = "tareas")
data class Tareas(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val descripcion: String = "",
    val tiempo: Int = 0
)

