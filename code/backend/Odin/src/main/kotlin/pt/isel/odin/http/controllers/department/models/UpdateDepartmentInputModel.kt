package pt.isel.odin.http.controllers.department.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a department.
 *
 * @property id The department id.
 * @property name The department name.
 */
data class UpdateDepartmentInputModel(
    val id: Long,

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String
)
