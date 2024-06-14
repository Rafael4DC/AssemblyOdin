package pt.isel.odin.http.controllers.department.models

import pt.isel.odin.model.Department

data class GetAllDepartmentsOutputModel(
    val departments: List<GetDepartmentOutputModel>
)

fun getAllDepartmentsOutputModel(departments: List<Department>): GetAllDepartmentsOutputModel {
    return GetAllDepartmentsOutputModel(
        departments.map {
            GetDepartmentOutputModel(it)
        }
    )
}
