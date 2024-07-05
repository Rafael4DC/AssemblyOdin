package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.CreditLog
import java.util.Optional

/**
 * Repository for Credit logs.
 */
@Repository
interface CreditLogRepository : JpaRepository<CreditLog, Long> {
    /**
     * Finds all logs by user id.
     *
     * @param userId the user id
     *
     * @return the list of logs
     */
    fun findByUserId(userId: Long): Optional<List<CreditLog>>
}
