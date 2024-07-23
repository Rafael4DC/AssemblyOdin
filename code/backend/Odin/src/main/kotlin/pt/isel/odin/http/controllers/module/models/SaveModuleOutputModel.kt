package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

/**
 * Represents the output model for saving a module.
 *
 * @property id The module id.
 *
 * @constructor Creates a [SaveModuleOutputModel] from a [Module].
 */
data class SaveModuleOutputModel(
    val id: Long
) {
    constructor(module: Module) : this(
        id = module.id!!
    )
}
