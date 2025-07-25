package com.bacano.WebApi.controllers.categoria

import com.bacano.WebApi.model.Categoria
import com.bacano.WebApi.services.CategoriaService
import com.bacano.WebApi.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/categorias")
class CategoriaController(
    private val categoriaService: CategoriaService,
    private val usuarioService: UsuarioService
) {
    // Endpoints consumidos por el admin
    @GetMapping
    fun getCategorias(): List<CategoriaResponse> =
        categoriaService.getAll().map { cat ->
            cat.toResponse()
        }

    // Endpoints consumidos por el usuario
    @PostMapping("/tipo-y-usuario")
    fun getCategoriaPorUsuarioYTipo(
        @RequestBody request: FiltroCategoriaRequest,
    ): List<CategoriaResponse> {
        val listaRetorno = categoriaService.getCategoriasByTipoAndUsuarioId(
            tipo = request.tipoCategoria,
            usuarioId = request.usuarioId
        )?.map {
            it.toResponse()
        } ?: emptyList()
        return listaRetorno
    }


    @GetMapping("/{categoriaId}")
    fun getCategoriaPorId(@PathVariable categoriaId: Long): CategoriaResponse =
        categoriaService.getCategoriaById(categoriaId)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Esta categorías no existe")

    @PostMapping
    fun postCategoria(@RequestBody request: CategoriaRequest): CategoriaResponse =
        categoriaService.createCategoria(
            categoria = request.toModel()
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta categoría ya existe")

    @PutMapping
    fun putCategoria(@RequestBody request: CategoriaRequest): CategoriaResponse {
        return categoriaService.updateCategoria(
            categoriaId = request.categoriaId,
            usuarioId = request.usuarioId,
            nuevaDescripcion = request.descripcion,
            nuevoTipo = request.tipoCategoria
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta categoría ya existe")
    }

    @DeleteMapping("/{categoriaId}")
    fun deleteCategoria(@PathVariable categoriaId: Long): Boolean {
        val categoriaEliminada = categoriaService.deleteCataegoriaById(categoriaId)
        if(categoriaEliminada)
            return true
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Esta categoría no existe")
    }

    private fun Categoria.toResponse(): CategoriaResponse =
        CategoriaResponse(
            categoriaId = this.categoriaId,
            tipoCategoria = this.tipoCategoria,
            descripcion = this.descripcion,
        )

    private fun CategoriaRequest.toModel(): Categoria {
        val user = usuarioService.getUserById(this.usuarioId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
        return Categoria(
            categoriaId = this.categoriaId,
            tipoCategoria = this.tipoCategoria,
            descripcion = this.descripcion,
            usuario = user,
        )
    }
}