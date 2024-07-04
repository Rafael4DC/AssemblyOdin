package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Tech

data class UpdateTechOutputModel(
    val id: Long,
    val teacher: GetUserOutputModel,
    val section: GetSectionOutputModel,
    val date: String,
    val summary: String,
    val missTech: List<GetUserOutputModel>
) {
    constructor(tech: Tech) : this(
        id = tech.id!!,
        teacher = GetUserOutputModel(tech.teacher),
        section = GetSectionOutputModel(tech.section),
        date = tech.date.toString(),
        summary = tech.summary,
        missTech = tech.missTech
            .map { GetUserOutputModel(it) }
    )
}
