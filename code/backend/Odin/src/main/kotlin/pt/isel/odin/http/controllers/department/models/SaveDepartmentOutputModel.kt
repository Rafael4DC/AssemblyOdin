package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.model.Department

data class SaveDepartmentOutputModel(
    val id: Long
) {
    constructor(department: Department) : this(
        id = department.id!!
    )
}