package com.bacano.WebApi.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "meta_Detalles")
data class MetaDetalle(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val metaDetalleId: Long = 0,
    val montoAporte: Double = 0.0,
    val fechaAporte: Date = Date(),

    @ManyToOne(optional = false)
    @JoinColumn(name = "meta_id")
    val meta: Meta? = null,
)
