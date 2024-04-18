package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.Tech

/**
 * Repository for techs.
 */
interface TechRepository : JpaRepository<Tech, Long>
