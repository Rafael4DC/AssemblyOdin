package pt.isel.odin.http.controllers.tech.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a tech.
 *
 * @property id The tech id.
 * @property teacher The tech teacher.
 * @property section The tech section.
 * @property date The tech date.
 * @property summary The tech summary.
 * @property missTech The tech missing students.
 */
data class UpdateTechInputModel(
    val id: Long,

    val teacher: Long? = null,

    val section: Long,

    val started: String,

    val ended: String,

    @NotBlank(message = "Summary is required")
    @Size(min = 1, max = 50, message = "Summary must have between 1 and 50 characters")
    val summary: String,

    val missTech: List<Long> = emptyList()
)
