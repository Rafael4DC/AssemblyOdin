import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {User} from "./models/User";
import {GetAllUsersOutputModel} from "./models/GetAllUsersOutputModel";

/**
 * Represents the result of a user get operation.
 */
export type GetUserResult = Either<Error | Problem, User>;

/**
 * Represents the result of a user get all operations.
 */
export type GetAllUsersResult = Either<Error | Problem, GetAllUsersOutputModel>;

/**
 * Represents the result of a user creation operation.
 */
export type CreationUserResult = Either<Error | Problem, User>;

/**
 * Represents the result of a user update operation.
 */
export type DeleteUserResult = Either<Error | Problem, User>;