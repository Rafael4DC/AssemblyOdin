package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.User
import pt.isel.odin.model.toRole

class UserRequest(
    val id: Long? = null,
    val email: String?,
    val username: String?,
    val role: String? = null
)

fun UserRequest.toUser() = User(
    id = id,
    email = email,
    username = username,
    role = role.toRole()
)
