package pt.isel.odin.service.user

import pt.isel.odin.model.CreditLog
import pt.isel.odin.model.Role
import pt.isel.odin.model.user.User
import pt.isel.odin.service.user.error.DeleteUserError
import pt.isel.odin.service.user.error.GetUserError
import pt.isel.odin.service.user.error.SaveUpdateUserError
import pt.isel.odin.utils.Either

/**
 * Represents the result of a user get operation.
 *
 * @see GetUserError
 * @see User
 */
typealias GetUserResult = Either<GetUserError, User>

/**
 * Represents the result of a user get with logs operation.
 *
 * @see GetUserError
 * @see Pair
 */
typealias GetUserWithLogsResult = Either<GetUserError, Pair<User, List<CreditLog>>>

/**
 * Represents the result of a user get all operations.
 *
 * @see GetUserError
 * @see List
 */
typealias GetAllUsersResult = Either<GetUserError, List<User>>

/**
 * Represents the result of a user creation operation.
 *
 * @see SaveUpdateUserError
 * @see User
 */
typealias CreationUserResult = Either<SaveUpdateUserError, User>

/**
 * Represents the result of a user delete operation.
 *
 * @see DeleteUserError
 * @see User
 */
typealias DeleteUserResult = Either<DeleteUserError, User>

/**
 * Represents the result of get all roles operation.
 *
 * @see SaveUpdateUserError
 * @see List
 */
typealias GetAllRolesResult = Either<GetUserError, List<Role>>
