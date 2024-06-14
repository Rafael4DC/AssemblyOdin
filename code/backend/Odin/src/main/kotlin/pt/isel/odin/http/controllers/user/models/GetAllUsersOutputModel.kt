package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

data class GetAllUsersOutputModel(
    val users: List<GetUserOutputModel>
)

fun getAllUsersOutputModel(users: List<User>): GetAllUsersOutputModel {
    return GetAllUsersOutputModel(
        users.map {
            GetUserOutputModel(it)
        }
    )
}
