package com.bacano.WebApi.services

import com.bacano.WebApi.model.RefreshToken
import com.bacano.WebApi.repositories.RefreshTokenRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userDetailsService: UserDetailsService
) {
    fun saveToken(token: String, userDetails: UserDetails) {
        val refreshToken = RefreshToken(
            token = token,
            username = userDetails.username,
            expiryDate = Instant.now().plus(7, ChronoUnit.DAYS)
        )
        refreshTokenRepository.save(refreshToken)
    }

    fun findUserDetailsByToken(token: String): UserDetails? {
        val refreshToken = refreshTokenRepository.findByToken(token) ?: return null
        return userDetailsService.loadUserByUsername(refreshToken.username)
    }

    fun deleteToken(token: String) {
        refreshTokenRepository.deleteByToken(token)
    }
}