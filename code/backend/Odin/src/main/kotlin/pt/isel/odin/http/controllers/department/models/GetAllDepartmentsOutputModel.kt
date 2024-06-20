package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.model.Department

/**
 * Represents the output model for getting all departments.
 *
 * @property departments The departments.
 */
data class GetAllDepartmentsOutputModel(
    val departments: List<GetDepartmentOutputModel>
)

/**
 * Creates a [GetAllDepartmentsOutputModel] from a list of [Department].
 *
 * @param departments The departments.
 *
 * @return The [GetAllDepartmentsOutputModel].
 */
fun getAllDepartmentsOutputModel(departments: List<Department>): GetAllDepartmentsOutputModel {
    return GetAllDepartmentsOutputModel(
        departments.map {
            GetDepartmentOutputModel(it)
        }
    )
}
