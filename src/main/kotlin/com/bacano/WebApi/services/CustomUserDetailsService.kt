package com.bacano.WebApi.services

import com.bacano.WebApi.repositories.UsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.bacano.WebApi.model.Usuario

@Service
class CustomUserDetailsService(
    private val userRepo: UsuarioRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepo.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("No se encontró")

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.rol.name)
            .build()
}