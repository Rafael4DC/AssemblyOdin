package pt.isel.odin.http.controllers.section.models

import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Section

/**
 * Represents the output model for updating a section.
 *
 * @property id The section id.
 * @property name The section name.
 * @property summary The section summary.
 * @property module The section module.
 * @property students The section students.
 *
 * @constructor Creates a [UpdateSectionOutputModel] from a [Section].
 */
data class UpdateSectionOutputModel(
    val id: Long,
    val name: String,
    val module: GetModuleOutputModel,
    val students: List<GetUserOutputModel>
) {
    constructor(section: Section) : this(
        id = section.id!!,
        name = section.name,
        module = GetModuleOutputModel(section.module!!),
        students = section.students
            .map { GetUserOutputModel(it) }
    )
}
