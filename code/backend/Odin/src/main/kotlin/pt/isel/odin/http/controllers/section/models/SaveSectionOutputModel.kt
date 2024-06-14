package pt.isel.odin.http.controllers.section.models

import pt.isel.odin.model.Section

data class SaveSectionOutputModel(
    val id: Long
) {
    constructor(section: Section) : this(
        id = section.id!!
    )
}
