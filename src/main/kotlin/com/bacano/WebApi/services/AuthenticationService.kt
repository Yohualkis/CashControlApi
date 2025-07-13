package com.bacano.WebApi.services

import com.bacano.WebApi.config.JwtProperties
import com.bacano.WebApi.controllers.auth.AuthenticationRequest
import com.bacano.WebApi.controllers.auth.AuthenticationResponse
import com.bacano.WebApi.controllers.usuario.UsuarioResponse
import com.bacano.WebApi.model.Usuario
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val userService: UsuarioService,
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        val userResponse = userService.getUserByEmail(authRequest.email)

        return AuthenticationResponse(
            accessToken = accessToken,
            usuario = userResponse!!.toResponse()
        )
    }

    private fun Usuario.toResponse() = UsuarioResponse(
        usuarioId = this.usuarioId,
        nombre = this.nombre,
        email = this.email,
        fotoPath = this.fotoPath,
        fechaRegistro = this.fechaRegistro
    )
}
