package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.Student

/**
 * Repository for students.
 */
interface StudentRepository : JpaRepository<Student, String>{
    fun findByEmail(email: String): Student?
}
