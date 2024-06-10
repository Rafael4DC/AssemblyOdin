package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.http.controllers.fieldstudy.models.GetFieldStudyOutputModel
import pt.isel.odin.model.Department

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