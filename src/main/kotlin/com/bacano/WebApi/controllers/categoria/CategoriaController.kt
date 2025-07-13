package com.bacano.WebApi.controllers.categoria

import com.bacano.WebApi.model.Categoria
import com.bacano.WebApi.services.CategoriaService
import com.bacano.WebApi.services.UsuarioService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Tag(name = "Categorías")
@RestController
@RequestMapping("api/categorias")
class CategoriaController(
    private val categoriaService: CategoriaService,
    private val usuarioService: UsuarioService
) {
    @GetMapping
    fun getCategorias(): List<CategoriaResponse> =
        categoriaService.getAll().map { cat ->
            cat.toResponse()
        }

    private fun Categoria.toResponse(): CategoriaResponse =
        CategoriaResponse(
            categoriaId = this.categoriaId,
            tipoCategoria = this.tipoCategoria,
            descripcion = this.descripcion,
            nombreUsuario = this.usuario.nombre,
        )

//    private fun CategoriaResponse.toModel(): Categoria {
//        // En esta linea se supone que debo obtener el usuario autenticado y pasarselo a categoria
//        Categoria(
//            categoriaId = TODO(),
//            tipoCategoria = TODO(),
//            descripcion = TODO(),
//            usuario = TODO()
//        )
//    }
}