package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.model.Tech

/**
 * Represents the output model for saving a tech.
 *
 * @property id The tech id.
 *
 * @constructor Creates a [SaveTechOutputModel] from a [Tech].
 */
data class SaveTechOutputModel(
    val id: Long
) {
    constructor(tech: Tech) : this(
        id = tech.id!!
    )
}
