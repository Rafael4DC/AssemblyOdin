package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.User

/**
 * Repository for users.
 */
interface UserRepository : JpaRepository<User, String>
