package pt.isel.odin.http.controllers.user.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Role
import pt.isel.odin.model.user.User

/**
 * Represents the input model for saving a user.
 *
 * @property email The user email.
 * @property username The user username.
 * @property credits The user credits.
 * @property role The user role.
 */
data class SaveUserInputModel(
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Size(min = 6, max = 320, message = "Email must have between 6 and 320 characters")
    val email: String,

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must have between 3 and 50 characters")
    val username: String,

    val credits: Int = 0,

    val role: Long
) {
    /**
     * Converts the [SaveUserInputModel] to a [User].
     *
     * @param role The [Role].
     *
     * @return The [User].
     */
    fun toUser(role: Role) = User(
        email = email,
        username = username,
        credits = credits,
        role = role
    )
}
