package pt.isel.odin.controller.dto.user

import pt.isel.odin.model.User

class UserRequest(
    val id: Long?,
    val username: String?,
    val email: String?
)

fun UserRequest.toUser() = User(
    id = id,
    username = username,
    email = email
)
