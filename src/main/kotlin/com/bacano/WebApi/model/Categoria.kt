package com.bacano.WebApi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "categorias")
data class Categoria(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoriaId: Long = 0,
    val tipoCategoria: String = "",
    val descripcion: String = "",

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario? = null
)