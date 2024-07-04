package pt.isel.odin.http.controllers.department.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Department

/**
 * Represents the input model for saving a department.
 *
 * @property name The department name.
 */
data class SaveDepartmentInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String
) {
    /**
     * Converts the [SaveDepartmentInputModel] to a [Department].
     *
     * @return The [Department].
     */
    fun toDepartment() = Department(name = name)
}
