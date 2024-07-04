package pt.isel.odin.http.controllers.voc.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Section
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

/**
 * Represents the input model for saving a voc.
 *
 * @property description The voc description.
 * @property approved The voc approved.
 * @property user The voc user.
 * @property section The voc section.
 * @property started The voc started.
 * @property ended The voc ended.
 */
data class SaveVocInputModel(
    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 500, message = "Description must have between 1 and 500 characters")
    val description: String,

    val approved: Boolean,

    val user: Long? = null,

    val section: Long,

    val started: String,

    val ended: String
) {
    /**
     * Converts the [SaveVocInputModel] to a [Voc].
     *
     * @param user The [User].
     * @param section The [Section].
     *
     * @return The [Voc].
     */
    fun toVoc(
        user: User,
        section: Section
    ) =
        Voc(
            description = description,
            approved = approved,
            user = user,
            section = section,
            started = LocalDateTime.parse(started),
            ended = LocalDateTime.parse(ended)
        )
}
