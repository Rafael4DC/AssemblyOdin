package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.CurricularUnit

/**
 * Repository for curricular units.
 */
@Repository
interface CurricularUnitRepository : JpaRepository<CurricularUnit, Long>{
    fun findByName(name: String): CurricularUnit?
}
