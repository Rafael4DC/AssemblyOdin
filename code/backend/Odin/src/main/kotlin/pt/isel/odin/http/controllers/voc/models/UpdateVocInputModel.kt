package pt.isel.odin.http.controllers.voc.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a voc.
 *
 * @property id The voc id.
 * @property description The voc description.
 * @property approved The voc approval status.
 * @property user The user id.
 * @property section The section id.
 * @property started The voc start date.
 * @property ended The voc end date.
 */
data class UpdateVocInputModel(
    val id: Long,

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 500, message = "Description must have between 1 and 500 characters")
    val description: String,

    val approved: Boolean,

    val user: Long? = null,

    val section: Long,

    val started: String,

    val ended: String
)
