package com.bacano.WebApi.services

import com.bacano.WebApi.model.Meta
import com.bacano.WebApi.model.MetaDetalle
import com.bacano.WebApi.repositories.MetaDetalleRepository
import com.bacano.WebApi.repositories.MetaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MetaService(
    private val metaRepo: MetaRepository,
    private val metaDetalleRepo: MetaDetalleRepository,
) {
    fun getAllMetas(): List<Meta> = metaRepo.findAll()

    fun guardarMeta(meta: Meta): Meta {
        getMetasByUsuarioId(
            meta.usuario?.usuarioId ?: throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Usuario no asignado"
            )
        )
            .firstOrNull {
                it.descripcion == meta.descripcion &&
                        it.usuario?.usuarioId == meta.usuario.usuarioId &&
                        it.metaId != meta.metaId
            }?.let {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Hubo un error al guardar la meta")
            }

        val nuevaMeta = metaEstaCompleta(meta)
        return metaRepo.save(nuevaMeta)
    }

    private fun metaEstaCompleta(meta: Meta): Meta {
        var totalDetalles = 0.0
        meta.detalles?.forEach {
            totalDetalles += it.montoAporte
        }

        return meta.copy(
            estaLograda = totalDetalles >= meta.montoObjetivo
        )
    }

    fun getMetasByEstaLograda(estanLogradas: Boolean, usuarioId: Long): List<Meta> =
        metaRepo.findAllByEstaLogradaAndUsuarioUsuarioId(estanLogradas, usuarioId)

    fun getMetasByUsuarioId(id: Long): List<Meta> =
        metaRepo.findAllByUsuarioUsuarioId(id)

    fun getMetaById(id: Long): Meta? =
        metaRepo.findByIdOrNull(id)

    fun eliminarMeta(id: Long): ResponseStatusException {
        getMetaById(id)?.let {
            metaRepo.delete(it)
            return ResponseStatusException(HttpStatus.NO_CONTENT, "Se ha eliminado la meta")
        }
        return ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada")
    }

    // Detalles
    fun getDetallesByMetaId(id: Long): List<MetaDetalle> = metaDetalleRepo.findByMetaIdWithMeta(id)

    fun guardarListaDetalles(detalles: List<MetaDetalle>): List<MetaDetalle> {
        val meta = getMetaById(
            detalles.firstOrNull()?.meta?.metaId
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Este detalle no tiene una meta")
        ) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Meta no encontrada")

        val totalActual = meta.detalles?.sumOf { it.montoAporte } ?: 0.0
        val totalPropuesto = totalActual.plus(detalles.sumOf { it.montoAporte })

        if (totalActual >= meta.montoObjetivo || totalPropuesto > meta.montoObjetivo)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La meta ya está completada o se excede el objetivo")

        if (totalPropuesto >= meta.montoObjetivo) {
            val nuevaMeta = meta.copy(estaLograda = true)
            metaRepo.save(nuevaMeta)
        }

        return metaDetalleRepo.saveAll(detalles)
    }

    fun eliminarDetalles(ids: List<Long>) {
        metaDetalleRepo
            .findAllById(ids)
            .ifEmpty {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se han encontrado los detalles")
            }
        metaDetalleRepo.deleteAllById(ids)
        throw ResponseStatusException(HttpStatus.NO_CONTENT, "Se ha eliminado el detalle")
    }
}

