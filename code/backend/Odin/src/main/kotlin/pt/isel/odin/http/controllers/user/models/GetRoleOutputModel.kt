package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.Role

data class GetRoleOutputModel(
    val id: Long,
    val name: String
) {
    constructor(role: Role) : this(
        id = role.id!!,
        name = role.name!!
    )
}
