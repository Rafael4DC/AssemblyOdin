package pt.isel.odin.http.controllers.tech.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateTechInputModel(
    val id: Long,

    val teacher: Long,

    val module: Long,

    val date: String,

    @NotBlank(message = "Summary is required")
    @Size(min = 1, max = 50, message = "Summary must have between 1 and 50 characters")
    val summary: String,

    val missTech: List<Long> = emptyList()
)