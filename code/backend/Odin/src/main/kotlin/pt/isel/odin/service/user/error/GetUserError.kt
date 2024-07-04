package pt.isel.odin.service.user.error

/**
 * Represents the user get error.
 */
sealed class GetUserError {
    data object NotFoundUser : GetUserError()
}
