package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.curricularunit.CurricularUnitRequest
import pt.isel.odin.model.CurricularUnit

/**
 * Service for curricular units.
 */
interface CurricularUnitService {

    /**
     * Gets a curricular unit by its id.
     *
     * @param id the curricular unit id.
     */
    fun getById(id: Long): CurricularUnit

    /**
     * Gets all curricular units.
     */
    fun getAll(): List<CurricularUnit>

    /**
     * Saves or Updates a curricular unit.
     *
     * @param curricularUnitRequest the curricular unit to save.
     */
    fun save(curricularUnitRequest: CurricularUnitRequest): CurricularUnit

    /**
     * Deletes a curricular unit by its id.
     *
     * @param id the curricular unit id.
     */
    fun delete(id: Long)
}
