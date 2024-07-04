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
    /**
     * Finds a department by its name.
     *
     * @param name the department name
     *
     * @return the [Department] if found, [Optional.empty] otherwise
     */
    fun findByName(name: String): Optional<Department>
}
