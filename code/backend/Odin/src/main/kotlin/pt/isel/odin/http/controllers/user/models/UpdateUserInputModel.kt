package pt.isel.odin.http.controllers.user.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents the input model for updating a user.
 *
 * @property id The user id.
 * @property email The user email.
 * @property username The user username.
 * @property credits The user credits.
 * @property role The user role.
 */
data class UpdateUserInputModel(
    val id: Long,

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Size(min = 6, max = 320, message = "Email must have between 6 and 320 characters")
    val email: String,

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must have between 3 and 50 characters")
    val username: String,

    val credits: Int = 0,

    val role: Long
)
