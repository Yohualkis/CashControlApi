package com.bacano.WebApi.services

import com.bacano.WebApi.model.Transaccion
import com.bacano.WebApi.repositories.TransaccionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TransaccionService(
    private val transRepo: TransaccionRepository
) {
    fun getAll() = transRepo.findAll()

    fun guardarTransaccion(transaccion: Transaccion) = transRepo.save(transaccion)

    fun getTransaccionById(id: Long): Transaccion? = transRepo.findByIdOrNull(id)

    fun getTransaccionPorCategoriaYUsuario(tipoCategoria: String, usuarioId: Long): List<Transaccion>? =
        transRepo.findByCategoriaTipoCategoriaAndUsuarioUsuarioId(
            tipoCategoria = tipoCategoria,
            usuarioId = usuarioId
        )

    fun eliminarTransaccionPorId(id: Long): Boolean {
        val transaccion = getTransaccionById(id)
            ?: return false

        transRepo.delete(transaccion)
        return true
    }
}