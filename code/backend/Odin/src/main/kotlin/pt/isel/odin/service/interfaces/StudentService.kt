package pt.isel.odin.service.interfaces

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
    fun getById(id: Long): Student

    /**
     * Gets all students.
     */
    fun getAll(): List<Student>

    /**
     * Saves or Updates a student.
     *
     * @param student the student to save.
     */
    fun save(student: Student): Student

    /**
     * Deletes a student by its id.
     *
     * @param id the student id.
     */
    fun delete(id: Long)
}
