package pt.isel.odin.http.controllers.module.models

import pt.isel.odin.model.Module

/**
 * Represents the output model for getting all modules.
 *
 * @property modules The modules.
 */
data class GetAllModulesOutputModel(
    val modules: List<GetModuleOutputModel>
)

/**
 * Creates a [GetAllModulesOutputModel] from a list of [Module].
 *
 * @param modules The modules.
 *
 * @return The [GetAllModulesOutputModel].
 */
fun getAllModulesOutputModel(modules: List<Module>): GetAllModulesOutputModel {
    return GetAllModulesOutputModel(
        modules.map {
            GetModuleOutputModel(it)
        }
    )
}
