package pt.isel.odin.http.controllers.section.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a section.
 *
 * @property id The section id.
 * @property name The section name.
 * @property module The section module.
 * @property students The section students.
 */
data class UpdateSectionInputModel(
    val id: Long,

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val module: Long,

    val students: List<Long> = emptyList()
)
