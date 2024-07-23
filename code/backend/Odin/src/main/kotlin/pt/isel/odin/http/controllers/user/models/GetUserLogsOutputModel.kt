package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.CreditLog
import pt.isel.odin.model.user.User

/**
 * Represents the output model for the user and logs.
 *
 * @property id the user id
 * @property email the user email
 * @property username the user username
 * @property credits the user credits
 * @property role the user role
 * @property logs the user logs
 *
 * @constructor Creates a GetUserLogsOutputModel instance.
 */
data class GetUserLogsOutputModel(
    val id: Long,
    val email: String,
    val username: String,
    val credits: Int,
    val role: GetRoleOutputModel,
    val logs: List<GetLogOutputModel>
) {
    constructor(user: User, logs: List<CreditLog>) : this(
        id = user.id!!,
        email = user.email,
        username = user.username,
        credits = user.credits,
        role = GetRoleOutputModel(user.role),
        logs = logs.map { GetLogOutputModel(it) }
    )
}
