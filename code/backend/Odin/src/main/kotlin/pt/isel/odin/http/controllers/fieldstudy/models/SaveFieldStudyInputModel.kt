package pt.isel.odin.http.controllers.fieldstudy.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy

data class SaveFieldStudyInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val department: Long
) {
    fun toFieldStudy(department: Department) =
        FieldStudy(
            department = department,
            name = name
        )
}