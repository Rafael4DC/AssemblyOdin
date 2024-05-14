package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Student

/**
 * Repository for students.
 */
@Repository
interface StudentRepository : JpaRepository<Student, String>{
    fun findByEmail(email: String): Student?
}
