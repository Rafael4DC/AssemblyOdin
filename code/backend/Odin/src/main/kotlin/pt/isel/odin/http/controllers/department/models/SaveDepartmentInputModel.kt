package pt.isel.odin.http.controllers.department.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Department

data class SaveDepartmentInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String
) {
    fun toDepartment() = Department(name = name)
}