package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.tech.TechAttendanceResponse
import pt.isel.odin.model.Tech

/**
 * Service for techs.
 */
interface TechService {

    /**
     * Gets a tech by its id.
     *
     * @param id the tech id.
     */
    fun getById(id: Long): Tech

    /**
     * Gets all techs.
     */
    fun getAll(): List<Tech>

    /**
     * Saves a tech.
     *
     * @param techRequest the tech info to save without id.
     */
    fun save(techRequest: Tech, email: String): Tech

    /**
     * Updates a tech.
     *
     * @param techRequest the tech info to update with id.
     */
    fun update(techRequest: Tech): Tech

    /**
     * Deletes a tech by its id.
     *
     * @param id the tech id.
     */
    fun delete(id: Long)

    /**
     * Gets all techs by User email.
     *
     * @param email the User email.
     */
    fun getByUser(email: String): List<Tech>

    /**
     * Gets all techs and students by User email.
     *
     * @param email the User email.
     */
    fun getMyTechsAttendance(email: String): List<TechAttendanceResponse>
}
