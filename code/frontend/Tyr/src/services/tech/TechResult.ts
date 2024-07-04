import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {Tech} from "./models/Tech";
import {GetAllTechsOutputModel} from "./models/GetAllTechsOutputModel";

/**
 * Represents the result of a tech get operation.
 */
export type GetTechResult = Either<Error | Problem, Tech>;

/**
 * Represents the result of a tech get all operations.
 */
export type GetAllTechsResult = Either<Error | Problem, GetAllTechsOutputModel>;

/**
 * Represents the result of a tech creation operation.
 */
export type CreationTechResult = Either<Error | Problem, Tech>;

/**
 * Represents the result of a tech schedule operation.
 */
export type ListCreationTechResult = Either<Error | Problem, Tech[]>;


/**
 * Represents the result of a tech update operation.
 */
export type DeleteTechResult = Either<Error | Problem, Tech>;