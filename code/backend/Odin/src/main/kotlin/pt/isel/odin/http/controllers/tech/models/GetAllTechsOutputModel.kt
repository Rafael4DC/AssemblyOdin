package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.model.Tech

/**
 * Represents the output model for getting all techs.
 *
 * @property techs The techs.
 *
 * @constructor Creates a [GetAllTechsOutputModel] from a list of [Tech].
 */
data class GetAllTechsOutputModel(
    val techs: List<GetTechOutputModel>
)

/**
 * Creates a [GetAllTechsOutputModel] from a list of [Tech].
 *
 * @param techs The techs.
 *
 * @return The [GetAllTechsOutputModel].
 */
fun getAllTechsOutputModel(techs: List<Tech>): GetAllTechsOutputModel {
    return GetAllTechsOutputModel(
        techs.map {
            GetTechOutputModel(it)
        }
    )
}
