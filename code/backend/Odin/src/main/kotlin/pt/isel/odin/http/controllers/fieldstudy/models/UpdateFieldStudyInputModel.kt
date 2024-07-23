package pt.isel.odin.http.controllers.fieldstudy.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a field study.
 *
 * @property id The field study id.
 * @property name The field study name.
 * @property department The department id.
 */
data class UpdateFieldStudyInputModel(
    val id: Long,

    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val department: Long
)
