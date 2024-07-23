package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.model.Department

/**
 * Represents the output model for saving a department.
 *
 * @property id The department id.
 *
 * @constructor Creates a [SaveDepartmentOutputModel] from a [Department].
 */
data class SaveDepartmentOutputModel(
    val id: Long
) {
    constructor(department: Department) : this(
        id = department.id!!
    )
}
