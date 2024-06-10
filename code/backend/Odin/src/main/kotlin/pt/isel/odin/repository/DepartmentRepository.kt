package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Department
import java.util.*

/**
 * Repository for Departments.
 */
@Repository
interface DepartmentRepository : JpaRepository<Department, Long> {
    fun findByName(name: String): Optional<Department>
}
