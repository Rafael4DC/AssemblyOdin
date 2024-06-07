package pt.isel.odin.service.user.error

/**
 * Represents the user creation error.
 */
sealed class SaveUserError {
    data object AlreadyExistsUser : SaveUserError()
    data object EmailIncorrectUser : SaveUserError()
    data object NameIncorrectUser : SaveUserError()
    data object RoleIncorrectUser : SaveUserError()
}
