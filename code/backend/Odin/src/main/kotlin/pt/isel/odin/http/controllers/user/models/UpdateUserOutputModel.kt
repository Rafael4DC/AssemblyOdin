package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

/**
 * Represents the output model for updating a user.
 *
 * @property id The user id.
 * @property email The user email.
 * @property username The user username.
 * @property role The user role.
 *
 * @constructor Creates a [UpdateUserOutputModel] from a [User].
 */
data class UpdateUserOutputModel(
    val id: Long,
    val username: String,
    val email: String,
    val role: GetRoleOutputModel
) {
    constructor(user: User) : this(
        id = user.id!!,
        username = user.username,
        email = user.email,
        role = GetRoleOutputModel(user.role)
    )
}
