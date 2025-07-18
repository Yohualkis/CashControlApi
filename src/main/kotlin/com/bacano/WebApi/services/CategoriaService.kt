package com.bacano.WebApi.services

import com.bacano.WebApi.model.Categoria
import com.bacano.WebApi.repositories.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val categoriaRepo: CategoriaRepository,
) {
    fun getAll(): List<Categoria> =
        categoriaRepo.findAll()

    fun getCategoriaById(id: Long): Categoria? =
        categoriaRepo.findByCategoriaId(id)

    fun createCategoria(categoria: Categoria): Categoria? {
        val existeCategoria = categoriaRepo.existeCategoria(
            descripcion = categoria.descripcion,
            tipoCategoria = categoria.tipoCategoria,
            usuarioId = categoria.usuario?.usuarioId ?: throw NullPointerException(),
            categoriaId = categoria.categoriaId,
        )

        return if (!existeCategoria)
            categoriaRepo.save(categoria)
        else null
    }

    fun updateCategoria(
        categoriaId: Long,
        usuarioId: Long,
        nuevaDescripcion: String? = null,
        nuevoTipo: String? = null
    ): Categoria? {
        val categoriaExistente = categoriaRepo.findByCategoriaId(categoriaId) ?: return null

        val descripcionFinal = nuevaDescripcion ?: categoriaExistente.descripcion
        val tipoFinal = nuevoTipo ?: categoriaExistente.tipoCategoria

        val existeDuplicado = categoriaRepo.existeCategoria(
            descripcion = descripcionFinal,
            tipoCategoria = tipoFinal,
            usuarioId = categoriaExistente.usuario?.usuarioId ?: throw NullPointerException(),
            categoriaId = categoriaExistente.categoriaId
        )

        if (existeDuplicado) return null

        return categoriaRepo.save(
            categoriaExistente.copy(
                descripcion = descripcionFinal,
                tipoCategoria = tipoFinal
            )
        )
    }


    fun getCategoriasByTipoAndUsuarioId(tipo: String, usuarioId: Long): List<Categoria>? {
        val categoriasFiltradas = categoriaRepo.findAllByTipoCategoriaYUsuarioId(
            tipoCategoria = tipo,
            usuarioId = usuarioId
        )
        return categoriasFiltradas.ifEmpty { null }
    }

    fun deleteCataegoriaById(id: Long): Boolean {
        val categoriaEncontrada = categoriaRepo.findByCategoriaId(id) ?: return false
        categoriaRepo.delete(categoriaEncontrada)
        return true
    }
}