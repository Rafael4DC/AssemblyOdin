import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {Module} from "./models/Module";
import {GetAllModulesOutputModel} from "./models/GetAllModulesOutputModel";

/**
 * Represents the result of a module get operation.
 */
export type GetModuleResult = Either<Error | Problem, Module>;

/**
 * Represents the result of a module get all operations.
 */
export type GetAllModulesResult = Either<Error | Problem, GetAllModulesOutputModel>;

/**
 * Represents the result of a module creation operation.
 */
export type CreationModuleResult = Either<Error | Problem, Module>;

/**
 * Represents the result of a module update operation.
 */
export type DeleteModuleResult = Either<Error | Problem, Module>;