package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.model.Tech

data class GetAllTechsOutputModel(
    val techs: List<GetTechOutputModel>
)

fun getAllTechsOutputModel(techs: List<Tech>): GetAllTechsOutputModel {
    return GetAllTechsOutputModel(
        techs.map {
            GetTechOutputModel(it)
        }
    )
}