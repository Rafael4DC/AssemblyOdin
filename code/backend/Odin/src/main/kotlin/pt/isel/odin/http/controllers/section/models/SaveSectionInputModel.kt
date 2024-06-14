package pt.isel.odin.http.controllers.section.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Module
import pt.isel.odin.model.Section
import pt.isel.odin.model.user.User

data class SaveSectionInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    @NotBlank(message = "Summary is required")
    @Size(min = 1, max = 500, message = "Summary must have between 1 and 500 characters")
    val summary: String,

    val module: Long,

    val students: List<Long> = emptyList()
) {
    fun toSection(
        students: MutableList<User>,
        module: Module
    ) =
        Section(
            name = name,
            summary = summary,
            module = module,
            students = students
        )
}
