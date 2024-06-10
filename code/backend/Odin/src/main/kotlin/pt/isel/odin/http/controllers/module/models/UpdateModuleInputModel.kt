package pt.isel.odin.http.controllers.module.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateModuleInputModel(
    val id: Long,

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val fieldStudy: Long,

    val tier: Int
)