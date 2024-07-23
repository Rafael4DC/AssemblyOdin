package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.http.controllers.fieldstudy.models.GetFieldStudyOutputModel
import pt.isel.odin.model.Department

/**
 * Represents the output model for updating a department.
 *
 * @property id The department id.
 * @property name The department name.
 * @property fieldsStudy The department fields study.
 *
 * @constructor Creates a [UpdateDepartmentOutputModel] from a [Department].
 */
data class UpdateDepartmentOutputModel(
    val id: Long,
    val name: String,
    val fieldsStudy: List<GetFieldStudyOutputModel>
) {
    constructor(department: Department) : this(
        id = department.id!!,
        name = department.name,
        fieldsStudy = department.fieldsStudy
            .map { GetFieldStudyOutputModel(it) }
    )
}
