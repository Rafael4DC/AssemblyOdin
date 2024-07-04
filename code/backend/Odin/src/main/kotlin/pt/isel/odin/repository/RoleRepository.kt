package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Role

/**
 * Repository for Roles.
 */
@Repository
interface RoleRepository : JpaRepository<Role, Long>
