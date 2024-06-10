package pt.isel.odin.http.controllers.fieldstudy.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateFieldStudyInputModel(
    val id: Long,

    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val department: Long
)