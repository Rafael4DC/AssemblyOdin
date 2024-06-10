package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

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