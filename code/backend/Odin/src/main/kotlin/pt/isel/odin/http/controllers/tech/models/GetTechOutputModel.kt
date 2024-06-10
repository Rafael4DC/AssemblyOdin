package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Tech

data class GetTechOutputModel(
    val id: Long,
    val teacher: GetUserOutputModel,
    val module: GetModuleOutputModel,
    val date: String,
    val summary: String,
    val missTech: List<GetUserOutputModel>
) {
    constructor(tech: Tech) : this(
        id = tech.id!!,
        teacher = GetUserOutputModel(tech.teacher),
        module = GetModuleOutputModel(tech.module),
        date = tech.date.toString(),
        summary = tech.summary,
        missTech = tech.missTech
            .map { GetUserOutputModel(it) }
    )
}
