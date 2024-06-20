package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Tech

/**
 * Represents the output model for updating a tech.
 *
 * @property id The tech id.
 * @property teacher The tech teacher.
 * @property section The tech section.
 * @property date The tech date.
 * @property summary The tech summary.
 * @property missTech The tech missing students.
 *
 * @constructor Creates a [UpdateTechOutputModel] from a [Tech].
 */
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
