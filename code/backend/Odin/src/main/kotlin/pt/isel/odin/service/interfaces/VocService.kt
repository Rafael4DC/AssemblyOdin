package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.voc.VocSaveInputModel
import pt.isel.odin.controller.dto.voc.VocUpdateInputModel
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
     * @param vocInputModel the voc info to save.
     */
    fun save(vocInputModel: VocSaveInputModel): Voc

    /**
     * Updates a voc.
     *
     * @param vocInputModel the voc info to update.
     */
    fun update(vocInputModel: VocUpdateInputModel): Voc

    /**
     * Deletes a voc by its id.
     *
     * @param id the voc id.
     */
    fun delete(id: Long)
}
