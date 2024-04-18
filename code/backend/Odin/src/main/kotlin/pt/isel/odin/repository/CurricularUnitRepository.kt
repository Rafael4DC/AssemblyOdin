package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.CurricularUnit

/**
 * Repository for curricular units.
 */
interface CurricularUnitRepository : JpaRepository<CurricularUnit, Long>
