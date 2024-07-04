package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

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
