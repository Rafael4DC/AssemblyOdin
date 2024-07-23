package pt.isel.odin.http.controllers.tech.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Section
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

/**
 * Represents the input model for saving a tech.
 *
 * @property teacher The tech teacher.
 * @property section The tech section.
 * @property started The tech started.
 * @property ended The tech ended.
 * @property summary The tech summary.
 * @property missTech The tech missing students.
 */
data class SaveTechInputModel(
    val teacher: Long? = null,

    val section: Long,

    val started: String,

    val ended: String,

    @NotBlank(message = "Summary is required")
    @Size(min = 1, max = 50, message = "Summary must have between 1 and 50 characters")
    val summary: String,

    val missTech: List<Long> = emptyList()
) {
    /**
     * Converts the [SaveTechInputModel] to a [Tech].
     *
     * @param teacher The [User].
     * @param section The [Section].
     * @param missTech The [User] list.
     *
     * @return The [Tech].
     */
    fun toTech(
        teacher: User,
        section: Section,
        missTech: MutableList<User>
    ) =
        Tech(
            teacher = teacher,
            section = section,
            started = LocalDateTime.parse(started),
            ended = LocalDateTime.parse(ended),
            summary = summary,
            missTech = missTech
        )
}
