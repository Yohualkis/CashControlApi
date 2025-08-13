package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.MetaDetalle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MetaDetalleRepository : JpaRepository<MetaDetalle, Long> {
    fun findByMetaMetaId(metaId: Long): List<MetaDetalle>

    @Query("""
        SELECT d FROM MetaDetalle d 
        JOIN FETCH d.meta 
        WHERE d.meta.metaId = :metaId
        """)
    fun findByMetaIdWithMeta(metaId: Long): List<MetaDetalle>
}