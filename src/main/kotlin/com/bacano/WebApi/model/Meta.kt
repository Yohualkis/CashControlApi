package com.bacano.WebApi.model

import jakarta.persistence.*

@Entity
@Table(name = "metas")
data class Meta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val metaId: Long = 0,
    val fotoPath: String? = null,
    val descripcion: String = "",
    val montoObjetivo: Double = 0.0,
    val estaLograda: Boolean = false,

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario? = null,

    @OneToMany(
        mappedBy = "meta",
        cascade = [CascadeType.ALL], // Operaciones CRUD con un detalle sin que quede huerfano de padre y madre
        orphanRemoval = true,
        fetch = FetchType.LAZY // esto es para que al momento de cargar muchas metas con sus detalles no sea muy pesado
    )
    val detalles: List<MetaDetalle>? = mutableListOf()
)
