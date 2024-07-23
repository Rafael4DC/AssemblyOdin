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
    /**
     * Finds a user by its email.
     *
     * @param email the user email
     *
     * @return the [User] if found, [Optional.empty] otherwise
     */
    fun findByEmail(email: String): Optional<User>

    /**
     * Finds a user by its role.
     *
     * @param roleName the user role name
     *
     * @return the [User] if found, [Optional.empty] otherwise
     */
    fun findUserByRole_NameIs(roleName: String): List<User>
}
