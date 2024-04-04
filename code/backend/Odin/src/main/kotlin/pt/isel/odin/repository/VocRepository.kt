package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.Voc

/**
 * Repository for vocs.
 */
interface VocRepository : JpaRepository<Voc, Long> {
}
