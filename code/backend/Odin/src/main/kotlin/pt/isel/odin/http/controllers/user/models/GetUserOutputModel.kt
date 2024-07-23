package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

/**
 * Represents the output model for getting a user.
 *
 * @property id The user id.
 * @property email The user email.
 * @property username The user username.
 * @property credits The user credits.
 * @property role The user role.
 *
 * @constructor Creates a [GetUserOutputModel] from a [User].
 */
data class GetUserOutputModel(
    val id: Long,
    val email: String,
    val username: String,
    val credits: Int,
    val role: GetRoleOutputModel
) {
    constructor(user: User) : this(
        id = user.id!!,
        email = user.email,
        username = user.username,
        credits = user.credits,
        role = GetRoleOutputModel(user.role)
    )
}
