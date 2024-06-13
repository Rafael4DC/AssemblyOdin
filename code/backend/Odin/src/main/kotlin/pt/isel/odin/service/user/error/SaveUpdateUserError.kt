package pt.isel.odin.service.user.error

/**
 * Represents the user creation error.
 */
sealed class SaveUpdateUserError {
    data object NotFoundUser : SaveUpdateUserError()
    data object IncorrectEmailUser : SaveUpdateUserError()
    data object EmailAlreadyExistsUser : SaveUpdateUserError()
    data object IncorrectNameUser : SaveUpdateUserError()
    data object RoleIncorrectUser : SaveUpdateUserError()
}
