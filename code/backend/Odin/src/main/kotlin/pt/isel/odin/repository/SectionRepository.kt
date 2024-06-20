package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Section
import java.util.*

/**
 * Repository for Sections.
 */
@Repository
interface SectionRepository : JpaRepository<Section, Long> {
    /**
     * Finds a section by its name.
     *
     * @param name the section name
     *
     * @return the [Section] if found, [Optional.empty] otherwise
     */
    fun findByName(name: String): Optional<Section>
}
