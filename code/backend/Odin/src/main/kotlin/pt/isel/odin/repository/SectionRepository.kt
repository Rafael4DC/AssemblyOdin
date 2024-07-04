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
    fun findByName(name: String): Optional<Section>
}
