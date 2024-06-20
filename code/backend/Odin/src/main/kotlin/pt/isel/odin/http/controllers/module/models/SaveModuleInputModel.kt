package pt.isel.odin.http.controllers.module.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module

/**
 * Represents the input model for saving a module.
 *
 * @property name The module name.
 * @property fieldStudy The field study id.
 * @property tier The module tier.
 */
data class SaveModuleInputModel(
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must have between 1 and 50 characters")
    val name: String,

    val fieldStudy: Long,

    val tier: Int
) {
    /**
     * Converts the [SaveModuleInputModel] to a [Module].
     *
     * @param fieldStudy The [FieldStudy].
     *
     * @return The [Module].
     */
    fun toModule(fieldStudy: FieldStudy) =
        Module(
            fieldStudy = fieldStudy,
            name = name,
            tier = tier
        )
}
