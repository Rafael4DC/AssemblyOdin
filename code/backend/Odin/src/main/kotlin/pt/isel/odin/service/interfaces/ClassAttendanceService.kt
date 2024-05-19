package pt.isel.odin.service.interfaces

import pt.isel.odin.model.ClassAttendance

/**
 * Service for class attendance.
 */
interface ClassAttendanceService {

    /**
     * Gets a class attendance by its id.
     *
     * @param id the class attendance id.
     */
    fun getById(id: Long): ClassAttendance

    /**
     * Gets all class attendances.
     */
    fun getAll(): List<ClassAttendance>

    /**
     * Saves a class attendance.
     *
     * @param classAttendRequest the class attendance info to save.
     */
    fun save(classAttendRequest: ClassAttendance): ClassAttendance

    /**
     * Updates a class attendance.
     *
     * @param classAttendRequest the class attendance info to update.
     */
    fun update(classAttendRequest: ClassAttendance): ClassAttendance

    /**
     * Deletes a class attendance by its id.
     *
     * @param id the class attendance id.
     */
    fun delete(id: Long)

    /**
     * Gets all class attendances by tech id.
     *
     * @param techId the tech id.
     */
    fun getByTechId(techId: Long): List<ClassAttendance>
}
