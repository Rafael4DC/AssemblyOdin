package pt.isel.odin.http.controllers.fieldstudy.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy

/**
 * Represents the input model for saving a field study.
 *
 * @property name The field study name.
 * @property department The department id.
 */
data class SaveFieldStudyInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val department: Long
) {
    /**
     * Converts the [SaveFieldStudyInputModel] to a [FieldStudy].
     *
     * @param department The [Department].
     *
     * @return The [FieldStudy].
     */
    fun toFieldStudy(department: Department) =
        FieldStudy(
            department = department,
            name = name
        )
}
