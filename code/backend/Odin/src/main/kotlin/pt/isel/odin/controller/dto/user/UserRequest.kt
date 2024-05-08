package pt.isel.odin.controller.dto.user

import pt.isel.odin.model.User

class UserRequest(
    /*val id: Long?,*/
    val email: String,
    val username: String?
)

fun UserRequest.toUser() = User(
    /*id = id,*/
    email = email,
    username = username

)
