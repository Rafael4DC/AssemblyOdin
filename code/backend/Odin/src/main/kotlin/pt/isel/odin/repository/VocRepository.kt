package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import java.util.*

/**
 * Repository for Vocs.
 */
@Repository
interface VocRepository : JpaRepository<Voc, Long> {
    /**
     * Finds all vocs of a user.
     *
     * @param user the user
     *
     * @return the list of [Voc] if found, [Optional.empty] otherwise
     */
    fun findByUser(user: User): Optional<List<Voc>>
}
