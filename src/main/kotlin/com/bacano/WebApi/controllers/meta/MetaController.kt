package com.bacano.WebApi.controllers.meta

import com.bacano.WebApi.model.Meta
import com.bacano.WebApi.model.MetaDetalle
import com.bacano.WebApi.services.MetaService
import com.bacano.WebApi.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/metas")
class MetaController(
    private val metaService: MetaService,
    private val usuarioService: UsuarioService,
) {
    @GetMapping
    fun getMetas(): List<MetaResponse> =
        metaService
            .getAllMetas()
            .map {
                it.toResponse()
            }.ifEmpty {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay metas...")
            }

    @PostMapping
    fun postMeta(@RequestBody request: MetaRequest): MetaResponse =
        metaService.guardarMeta(request.toModel()).toResponse()

    @PutMapping
    fun putMeta(@RequestBody request: MetaRequest): MetaResponse =
        metaService.guardarMeta(request.toModel()).toResponse()

    @DeleteMapping("/{metaId}")
    fun deleteMeta(@PathVariable metaId: Long): ResponseStatusException =
        metaService.eliminarMeta(metaId)

    // Filtro completas o no completas
    @GetMapping("/logradas")
    fun getMetasCompletadas(@RequestBody request: FiltroMetasLogradasPorUsuarioRequest): List<MetaResponse> =
        metaService.getMetasByEstaLograda(
            estanLogradas = request.estanLogradas,
            usuarioId = request.usuarioId
        ).map {
            it.toResponse()
        }

    // Detalles
    @GetMapping("/detalles/{metaId}")
    fun getMetaDetalles(@PathVariable metaId: Long): List<MetaDetalleResponse> =
        metaService.getDetallesByMetaId(metaId)
            .map {
                it.toResponse()
            }.ifEmpty {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Esta meta no tiene detalle...")
            }

    @PostMapping("/detalles")
    fun postDetalles(@RequestBody listaDetalles: List<MetaDetalleRequest>): List<MetaDetalleResponse> =
        metaService.guardarListaDetalles(
            detalles = listaDetalles
                .map {
                    it.toModel()
                }.ifEmpty {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes agregar una lista vacía")
                }
        ).map {
            it.toResponse()
        }

    @PutMapping("/detalles")
    fun putDetalles(@RequestBody listaDetalles: List<MetaDetalleRequest>): List<MetaDetalleResponse> =
        metaService.guardarListaDetalles(
            detalles = listaDetalles
                .map {
                    it.toModel()
                }.ifEmpty {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes dejar la lista vacía")
                }
        ).map {
            it.toResponse()
        }

    @DeleteMapping("/detalles")
    fun deleteDetalles(@RequestBody listaIdsDetalle: List<Long>) =
            metaService.eliminarDetalles(listaIdsDetalle)

    private fun Meta.toResponse(): MetaResponse =
        MetaResponse(
            metaId = this.metaId,
            fotoPath = this.fotoPath,
            descripcion = this.descripcion,
            montoMeta = this.montoObjetivo,
            usuarioId = this.usuario?.usuarioId
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"),
            estaLograda = this.estaLograda,
        )

    private fun MetaRequest.toModel(): Meta {
        val user = usuarioService.getUserById(this.usuarioId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")

        val detalles = metaService.getMetaById(this.metaId)?.detalles
            ?: emptyList()

        return Meta(
            metaId = this.metaId,
            fotoPath = this.fotoPath,
            descripcion = this.descripcion,
            montoObjetivo = this.montoMeta,
            usuario = user,
            detalles = detalles,
            estaLograda = this.estaLograda
        )
    }

    private fun MetaDetalle.toResponse() =
        MetaDetalleResponse(
            metaDetalleId = this.metaDetalleId,
            metaId = this.meta?.metaId
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"),
            montoAporte = this.montoAporte,
            fechaAporte = this.fechaAporte
        )

    private fun MetaDetalleRequest.toModel(): MetaDetalle =
        MetaDetalle(
            metaDetalleId = this.metaDetalleId,
            montoAporte = this.montoAporte,
            fechaAporte = this.fechaAporte,
            meta = metaService.getMetaById(this.metaId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada")
        )
}