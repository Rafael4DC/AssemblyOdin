package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Tech

/**
 * Represents the output model for getting a tech.
 *
 * @property id The tech id.
 * @property teacher The tech teacher.
 * @property section The tech section.
 * @property date The tech date.
 * @property summary The tech summary.
 * @property missTech The tech missing students.
 *
 * @constructor Creates a [GetTechOutputModel] from a [Tech].
 */
data class GetTechOutputModel(
    val id: Long,
    val teacher: GetUserOutputModel,
    val section: GetSectionOutputModel,
    val started : String,
    val ended : String,
    val summary: String,
    val missTech: List<GetUserOutputModel>
) {
    constructor(tech: Tech) : this(
        id = tech.id!!,
        teacher = GetUserOutputModel(tech.teacher),
        section = GetSectionOutputModel(tech.section),
        started = tech.started.toString(),
        ended = tech.ended.toString(),
        summary = tech.summary,
        missTech = tech.missTech
            .map { GetUserOutputModel(it) }
    )
}
