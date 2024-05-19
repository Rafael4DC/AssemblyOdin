package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.SubCategory

/**
 * Repository for curricular units.
 */
@Repository
interface SubCategoryRepository : JpaRepository<SubCategory, Long>
