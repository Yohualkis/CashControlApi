package com.bacano.WebApi.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val token: String = "",

    @Column(nullable = false)
    val username: String = "",

    @Column(nullable = false)
    val expiryDate: Instant = Instant.now()
)