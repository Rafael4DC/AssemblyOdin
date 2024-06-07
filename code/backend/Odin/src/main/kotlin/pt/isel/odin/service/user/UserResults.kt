package pt.isel.odin.service.user

import pt.isel.odin.model.User
import pt.isel.odin.service.user.error.DeleteUserError
import pt.isel.odin.service.user.error.SaveUserError
import pt.isel.odin.service.user.error.GetUserError
import pt.isel.odin.utils.Either

/**
 * Represents the result of a user get operation.
 *
 * @see GetUserError
 * @see User
 */
typealias GetUserResult = Either<GetUserError, User>

/**
 * Represents the result of a user creation operation.
 *
 * @see SaveUserError
 * @see User
 */
typealias CreationUserResult = Either<SaveUserError, User>

/**
 * Represents the result of a user update operation.
 *
 * @see SaveUserError
 * @see User
 */
typealias UpdateUserResult = Either<SaveUserError, User>

/**
 * Represents the result of a user delete operation.
 *
 * @see DeleteUserError
 * @see User
 */
typealias DeleteUserResult = Either<DeleteUserError, User>