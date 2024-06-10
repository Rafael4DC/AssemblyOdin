package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.model.Tech

data class SaveTechOutputModel(
    val id: Long
) {
    constructor(tech: Tech) : this(
        id = tech.id!!
    )
}