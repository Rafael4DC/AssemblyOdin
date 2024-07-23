package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

/**
 * Represents the output model for saving a user.
 *
 * @property id The user id.
 *
 * @constructor Creates a [SaveUserOutputModel] from a [User].
 */
data class SaveUserOutputModel(
    val id: Long
) {
    constructor(user: User) : this(
        id = user.id!!
    )
}
