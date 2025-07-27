package com.bacano.WebApi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "transacciones")
data class Transaccion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val transaccionId: Long = 0,
    val monto: Double = 0.0,
    val fechaTransaccion: Date = Date(),
    val usarSugerencia: Boolean = true,
    val descripcion: String = "",

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoriaId")
    val categoria: Categoria? = null
)