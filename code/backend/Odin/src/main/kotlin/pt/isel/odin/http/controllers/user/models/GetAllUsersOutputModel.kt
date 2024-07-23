package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

/**
 * Represents the output model for getting all users.
 *
 * @property users The users.
 *
 * @constructor Creates a [GetAllUsersOutputModel] from a list of [User].
 */
data class GetAllUsersOutputModel(
    val users: List<GetUserOutputModel>
)

/**
 * Creates a [GetAllUsersOutputModel] from a list of [User].
 *
 * @param users The users.
 *
 * @return The [GetAllUsersOutputModel].
 */
fun getAllUsersOutputModel(users: List<User>): GetAllUsersOutputModel {
    return GetAllUsersOutputModel(
        users.map {
            GetUserOutputModel(it)
        }
    )
}
