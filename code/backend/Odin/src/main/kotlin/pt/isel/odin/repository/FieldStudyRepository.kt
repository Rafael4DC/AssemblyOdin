package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.FieldStudy
import java.util.*

/**
 * Repository for Fields Of Study.
 */
@Repository
interface FieldStudyRepository : JpaRepository<FieldStudy, Long> {
    /**
     * Finds a field of study by its name.
     *
     * @param name the field of study name
     *
     * @return the [FieldStudy] if found, [Optional.empty] otherwise
     */
    fun findByName(name: String): Optional<FieldStudy>
}
