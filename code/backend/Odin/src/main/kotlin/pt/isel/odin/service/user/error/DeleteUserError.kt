package pt.isel.odin.service.user.error

/**
 * Represents the user delete error.
 */
sealed class DeleteUserError {
    data object NotFoundUser : DeleteUserError()
}