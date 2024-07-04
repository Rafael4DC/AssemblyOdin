package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import java.util.Optional

/**
 * Repository for Vocs.
 */
@Repository
interface VocRepository : JpaRepository<Voc, Long> {
    fun findByUser(user: User): Optional<List<Voc>>
}
