package pt.isel.odin.http.controllers.section.models

import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Section

/**
 * Represents the output model for getting a section.
 *
 * @property id The section id.
 * @property name The section name.
 * @property summary The section summary.
 * @property module The section module.
 * @property students The section students.
 *
 * @constructor Creates a [GetSectionOutputModel] from a [Section].
 */
data class GetSectionOutputModel(
    val id: Long,
    val name: String,
    val summary: String,
    val module: GetModuleOutputModel,
    val students: List<GetUserOutputModel>
) {
    constructor(section: Section) : this(
        id = section.id!!,
        name = section.name,
        summary = section.summary,
        module = GetModuleOutputModel(section.module!!),
        students = section.students
            .map { GetUserOutputModel(it) }
    )
}
