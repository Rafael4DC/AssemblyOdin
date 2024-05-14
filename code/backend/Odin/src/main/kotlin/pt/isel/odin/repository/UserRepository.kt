package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.User

/**
 * Repository for users.
 */
@Repository
interface UserRepository : JpaRepository<User, String>{
    fun findByEmail(email: String): User?
}
