package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CategoriaRepository: JpaRepository<Categoria, Long> {
    @Query("""
    select exists (
        select 1 from Categoria c 
        where c.descripcion = :descripcion 
        and c.tipoCategoria = :tipoCategoria 
        and c.usuario.usuarioId = :usuarioId
    )
""")
    fun existsByDescripcionAndTipoCategoriaAndUsuarioId(
        @Param("descripcion") descripcion: String,
        @Param("tipoCategoria") tipoCategoria: String,
        @Param("usuarioId") usuarioId: Long
    ): Boolean

    @Query("""
        select c from Categoria c
        where c.tipoCategoria = :tipoCategoria
        and c.usuario.usuarioId = :usuarioId
    """)
    fun findAllByTipoCategoriaYUsuarioId(
        @Param("tipoCategoria") tipoCategoria: String,
        @Param("usuarioId") usuarioId: Long
    ): List<Categoria>

    fun findByCategoriaId(id: Long): Categoria?

    @Query("""
        select count(c) > 0
        from Categoria c
        where c.descripcion = :descripcion
          and c.tipoCategoria = :tipoCategoria
          and c.usuario.usuarioId = :usuarioId
          and c.categoriaId != :categoriaId
    """)
    fun existeCategoria(
        @Param("descripcion") descripcion: String,
        @Param("tipoCategoria") tipoCategoria: String,
        @Param("usuarioId") usuarioId: Long,
        @Param("categoriaId") categoriaId: Long
    ): Boolean
}