package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

data class SaveModuleOutputModel(
    val id: Long
) {
    constructor(module: Module) : this(
        id = module.id!!
    )
}