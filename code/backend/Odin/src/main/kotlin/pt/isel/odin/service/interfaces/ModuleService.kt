package pt.isel.odin.service.interfaces

import pt.isel.odin.model.Module

/**
 * Service for Modules.
 */
interface ModuleService {

    /**
     * Gets a Module by its id.
     *
     * @param id the Module id.
     */
    fun getById(id: Long): Module

    /**
     * Gets all Modules.
     */
    fun getAll(): List<Module>

    /**
     * Saves or Updates a Module.
     *
     * @param moduleRequest the Module to save.
     */
    fun save(moduleRequest: Module): Module

    /**
     * Deletes a Module by its id.
     *
     * @param id the Module id.
     */
    fun delete(id: Long)
}
