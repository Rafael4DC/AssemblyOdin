package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.student.StudentRequest
import pt.isel.odin.model.Student

/**
 * Service for students.
 */
interface StudentService {

    /**
     * Gets a student by its id.
     *
     * @param id the student id.
     */
    fun getById(id: String): Student

    /**
     * Gets a student by its email.
     *
     * @param email the student email.
     */
    fun getByEmail(email: String): Student?

    /**
     * Gets all students.
     */
    fun getAll(): List<Student>

    /**
     * Saves or Updates a student.
     *
     * @param studentRequest the student to save.
     */
    fun save(studentRequest: StudentRequest): Student

    /**
     * Deletes a student by its id.
     *
     * @param id the student id.
     */
    fun delete(id: String)
}
