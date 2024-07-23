package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

/**
 * Represents the output model for getting a module.
 *
 * @property id The module id.
 * @property name The module name.
 * @property tier The module tier.
 *
 * @constructor Creates a [GetModuleOutputModel] from a [Module].
 */
data class GetModuleOutputModel(
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
