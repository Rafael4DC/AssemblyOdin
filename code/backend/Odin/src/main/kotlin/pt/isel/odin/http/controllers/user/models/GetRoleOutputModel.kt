package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.Role

/**
 * Represents the output model for getting a role.
 *
 * @property id The role id.
 * @property name The role name.
 *
 * @constructor Creates a [GetRoleOutputModel] from a [Role].
 */
data class GetRoleOutputModel(
    val id: Long,
    val name: String
) {
    constructor(role: Role) : this(
        id = role.id!!,
        name = role.name!!
    )
}
