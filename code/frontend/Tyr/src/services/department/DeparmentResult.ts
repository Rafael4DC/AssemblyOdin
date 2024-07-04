import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {Department} from "./models/Department";
import {GetAllDepartmentsOutputModel} from "./models/GetAllDepartmentsOutputModel";

/**
 * Represents the result of a department get operation.
 */
export type GetDepartmentResult = Either<Error | Problem, Department>;

/**
 * Represents the result of a department get all operations.
 */
export type GetAllDepartmentsResult = Either<Error | Problem, GetAllDepartmentsOutputModel>;

/**
 * Represents the result of a department creation operation.
 */
export type CreationDepartmentResult = Either<Error | Problem, Department>;

/**
 * Represents the result of a department update operation.
 */
export type DeleteDepartmentResult = Either<Error | Problem, Department>;