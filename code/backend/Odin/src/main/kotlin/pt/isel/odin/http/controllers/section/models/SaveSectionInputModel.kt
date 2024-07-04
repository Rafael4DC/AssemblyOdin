package pt.isel.odin.http.controllers.section.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Module
import pt.isel.odin.model.Section
import pt.isel.odin.model.user.User

/**
 * Represents the input model for saving a section.
 *
 * @property name The section name.
 * @property summary The section summary.
 * @property module The section module.
 * @property students The section students.
 */
data class SaveSectionInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val module: Long,

    val students: List<Long> = emptyList()
) {
    /**
     * Converts the [SaveSectionInputModel] to a [Section].
     *
     * @param students The [User] list.
     * @param module The [Module].
     *
     * @return The [Section].
     */
    fun toSection(
        students: MutableList<User>,
        module: Module
    ) =
        Section(
            name = name,
            module = module,
            students = students
        )
}
