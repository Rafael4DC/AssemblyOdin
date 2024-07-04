package pt.isel.odin.http.controllers.section.models

import pt.isel.odin.model.Section

/**
 * Represents the output model for saving a section.
 *
 * @property id The section id.
 *
 * @constructor Creates a [SaveSectionOutputModel] from a [Section].
 */
data class SaveSectionOutputModel(
    val id: Long
) {
    constructor(section: Section) : this(
        id = section.id!!
    )
}
