package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.voc.VocRequest
import pt.isel.odin.model.Voc

/**
 * Service for vocs.
 */
interface VocService {

    /**
     * Gets a voc by its id.
     *
     * @param id the voc id.
     */
    fun getById(id: Long): Voc

    /**
     * Gets all vocs.
     */
    fun getAll(): List<Voc>

    /**
     * Saves a voc.
     *
     * @param vocRequest the voc info to save.
     * @param email the User.
     */
    fun save(vocRequest: Voc, email: String): Voc

    /**
     * Updates a voc.
     *
     * @param vocRequest the voc info to update.
     */
    fun update(vocRequest: VocRequest): Voc

    /**
     * Deletes a voc by its id.
     *
     * @param id the voc id.
     */
    fun delete(id: Long)

    /**
     * Gets all vocs by User.
     *
     * @param email the User.
     */
    fun getByStudent(email: String): List<Voc>
}
