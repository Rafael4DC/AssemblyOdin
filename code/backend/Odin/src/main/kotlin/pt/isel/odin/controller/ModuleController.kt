package pt.isel.odin.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.model.Module
import pt.isel.odin.service.interfaces.ModuleService

/**
 * Represents the controller that contains the endpoints related to the Modules.
 */
@RestController
@RequestMapping("/api/modules")
class ModuleController(private val moduleService: ModuleService) {

    /**
     * Get a Module by id
     *
     * @param id the Module id
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Module? {
        return moduleService.getById(id)
    }

    /**
     * Get all Modules
     */
    @GetMapping
    fun getAll(): List<Module> {
        return moduleService.getAll()
    }

    /**
     * Save a Module
     *
     * @param moduleRequest the Module to save
     */
    @PostMapping("/save")
    fun save(@RequestBody moduleRequest: Module): Module {
        return moduleService.save(moduleRequest)
    }

    /**
     * Update a Module
     *
     * @param moduleRequest the Module to update
     */
    @PutMapping("/update")
    fun update(@RequestBody moduleRequest: Module): Module {
        return moduleService.save(moduleRequest)
    }

    /**
     * Delete a Module by id
     *
     * @param id the Module id
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        moduleService.delete(id)
    }
}
