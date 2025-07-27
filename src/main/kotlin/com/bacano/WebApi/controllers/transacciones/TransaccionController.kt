package com.bacano.WebApi.controllers.transacciones

import com.bacano.WebApi.model.Transaccion
import com.bacano.WebApi.services.CategoriaService
import com.bacano.WebApi.services.TransaccionService
import com.bacano.WebApi.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/transacciones")
class TransaccionController(
    private val transService: TransaccionService,
    private val userService: UsuarioService,
    private val categoriaService: CategoriaService,
) {

    @GetMapping
    fun getTransacciones(): List<TransaccionResponse> =
        transService.getAll().map {
            it.toResponse()
        }

    @GetMapping("/{transaccionId}")
    fun getTransaccionById(@PathVariable transaccionId: Long): TransaccionResponse =
        transService.getTransaccionById(transaccionId)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Transaccion no encontrada")

    @PostMapping
    fun postTransaccion(@RequestBody request: TransaccionRequest): TransaccionResponse {
        val transaccion = transService.guardarTransaccion(
            request.toModel(
                userId = request.usuarioId,
                categoriaId = request.categoriaId
            )
        )
        return transaccion.toResponse()
    }

    @PutMapping
    fun putTransaccion(@RequestBody request: TransaccionRequest): TransaccionResponse {
        if(transService.getTransaccionById(request.transaccionId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Esta transacción no existe")

        return transService.guardarTransaccion(
            request.toModel(
                userId = request.usuarioId,
                categoriaId = request.categoriaId
            )
        ).toResponse()
    }

    @DeleteMapping("/{transaccionId}")
    fun deleteTransaccion(@PathVariable transaccionId: Long): Boolean {
        val transaccionEliminada = transService.eliminarTransaccionPorId(transaccionId)
        if(!transaccionEliminada)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Esta transacción no existe")
        return true
    }

    private fun Transaccion.toResponse() = TransaccionResponse(
        transaccionId = this.transaccionId,
        categoriaId = this.categoria?.categoriaId ?: 0,
        usuarioId = this.usuario?.usuarioId ?: 0,
        monto = this.monto,
        fechaTransaccion = this.fechaTransaccion,
        usarSugerencia = this.usarSugerencia,
        descripcion = this.descripcion,
    )

    private fun TransaccionRequest.toModel(userId: Long, categoriaId: Long): Transaccion {
        val user = userService.getUserById(userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")

        val categoria = categoriaService.getCategoriaById(categoriaId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")

        return Transaccion(
            transaccionId = this.transaccionId,
            usuario = user,
            categoria = categoria,
            monto = this.monto,
            fechaTransaccion = this.fechaTransaccion,
            usarSugerencia = this.usarSugerencia,
            descripcion = this.descripcion,
        )
    }
}