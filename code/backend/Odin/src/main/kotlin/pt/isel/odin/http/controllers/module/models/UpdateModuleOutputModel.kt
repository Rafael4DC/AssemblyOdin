package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

/**
 * Represents the output model for updating a module.
 *
 * @property id The module id.
 * @property name The module name.
 * @property tier The module tier.
 *
 * @constructor Creates a [UpdateModuleOutputModel] from a [Module].
 */
data class UpdateModuleOutputModel(
    val id: Long,
    val name: String,
    val tier: Int
) {
    constructor(module: Module) : this(
        id = module.id!!,
        name = module.name,
        tier = module.tier
    )
}
