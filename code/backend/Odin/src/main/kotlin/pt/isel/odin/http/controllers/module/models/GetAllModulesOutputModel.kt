package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

data class GetAllModulesOutputModel(
    val modules: List<GetModuleOutputModel>
)

fun getAllModulesOutputModel(modules: List<Module>): GetAllModulesOutputModel {
    return GetAllModulesOutputModel(
        modules.map {
            GetModuleOutputModel(it)
        }
    )
}