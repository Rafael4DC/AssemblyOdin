package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Module

/**
 * Repository for Modules.
 */
@Repository
interface ModuleRepository : JpaRepository<Module, Long>
