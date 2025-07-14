package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): RefreshToken?
    fun deleteByToken(token: String)
    fun findByUsername(username: String): List<RefreshToken>
}