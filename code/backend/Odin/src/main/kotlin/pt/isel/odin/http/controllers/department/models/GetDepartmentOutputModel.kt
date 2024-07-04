package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.http.controllers.fieldstudy.models.GetFieldStudyOutputModel
import pt.isel.odin.model.Department

/**
 * Represents the output model for getting a department.
 *
 * @property id The department id.
 * @property name The department name.
 * @property fieldsStudy The department fields study.
 *
 * @constructor Creates a [GetDepartmentOutputModel] from a [Department].
 */
data class GetDepartmentOutputModel(
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
