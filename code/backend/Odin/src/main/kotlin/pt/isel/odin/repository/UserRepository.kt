package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.user.User
import java.util.*

/**
 * Repository for Users.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>

    fun findUserByRole_NameIs(roleName: String): List<User>
}
