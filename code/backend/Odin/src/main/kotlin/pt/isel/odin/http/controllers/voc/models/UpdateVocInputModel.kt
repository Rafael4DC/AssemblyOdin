package pt.isel.odin.http.controllers.voc.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateVocInputModel(
    val id: Long,

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 500, message = "Description must have between 1 and 500 characters")
    val description: String,

    val approved: Boolean,

    val user: Long? = null,

    val module: Long,

    val started: String,

    val ended: String
)