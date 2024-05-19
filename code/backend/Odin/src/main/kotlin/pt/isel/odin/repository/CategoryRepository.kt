package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Category

/**
 * Repository for curricular units.
 */
@Repository
interface CategoryRepository : JpaRepository<Category, Long>
