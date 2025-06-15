package com.bacano.WebApi.services

import com.bacano.WebApi.model.Tareas
import com.bacano.WebApi.repositories.TareasRepository
import org.springframework.stereotype.Service

@Service
class TareasService(
    private val repository: TareasRepository
) {
    fun getAll(): List<Tareas> = repository.findAll()
}