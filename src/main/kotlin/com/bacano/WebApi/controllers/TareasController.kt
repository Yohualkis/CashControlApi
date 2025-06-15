package com.bacano.WebApi.controllers

import com.bacano.WebApi.model.Tareas
import com.bacano.WebApi.services.TareasService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tareas")
class TareasController(
    private val tareasService: TareasService
) {
    @GetMapping
    fun getTareas(): List<Tareas> = tareasService.getAll()
}