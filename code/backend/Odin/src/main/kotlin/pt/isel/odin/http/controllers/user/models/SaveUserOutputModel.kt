package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.user.User

data class SaveUserOutputModel(
    val id: Long
) {
    constructor(user: User) : this(
        id = user.id!!
    )
}