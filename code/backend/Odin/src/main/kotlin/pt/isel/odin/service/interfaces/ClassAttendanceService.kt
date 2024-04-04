package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.classattendance.ClassAttendSaveInputModel
import pt.isel.odin.controller.dto.classattendance.ClassAttendUpdateInputModel
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
     * @param classAttendInputModel the class attendance info to save.
     */
    fun save(classAttendInputModel: ClassAttendSaveInputModel): ClassAttendance

    /**
     * Updates a class attendance.
     *
     * @param classAttendInputModel the class attendance info to update.
     */
    fun update(classAttendInputModel: ClassAttendUpdateInputModel): ClassAttendance

    /**
     * Deletes a class attendance by its id.
     *
     * @param id the class attendance id.
     */
    fun delete(id: Long)
}
