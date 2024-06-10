package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

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
