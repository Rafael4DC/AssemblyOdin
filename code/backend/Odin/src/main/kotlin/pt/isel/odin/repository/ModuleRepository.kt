package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Module
import java.util.*

/**
 * Repository for Modules.
 */
@Repository
interface ModuleRepository : JpaRepository<Module, Long> {
    /**
     * Finds a module by its name.
     *
     * @param name the module name
     *
     * @return the [Module] if found, [Optional.empty] otherwise
     */
    fun findByName(name: String): Optional<Module>
}
