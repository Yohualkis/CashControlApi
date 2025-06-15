package com.bacano.WebApi.repositories

import com.bacano.WebApi.model.Tareas
import org.springframework.data.jpa.repository.JpaRepository

interface TareasRepository: JpaRepository<Tareas, Int> {
}