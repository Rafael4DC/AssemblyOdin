package pt.isel.odin.http.controllers.section.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a section.
 *
 * @property id The section id.
 * @property name The section name.
 * @property summary The section summary.
 * @property module The section module.
 * @property students The section students.
 */
data class UpdateSectionInputModel(
    val id: Long,

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    @NotBlank(message = "Summary is required")
    @Size(min = 1, max = 500, message = "Summary must have between 1 and 500 characters")
    val summary: String,

    val module: Long,

    val students: List<Long> = emptyList()
)
