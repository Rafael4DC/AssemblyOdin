import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {FieldStudy} from "./models/FieldStudy";
import {GetAllFieldsStudyOutputModel} from "./models/GetAllFieldsStudyOutputModel";

/**
 * Represents the result of a field of study get operation.
 */
export type GetFieldStudyResult = Either<Error | Problem, FieldStudy>;

/**
 * Represents the result of a field of study get all operations.
 */
export type GetAllFieldsStudyResult = Either<Error | Problem, GetAllFieldsStudyOutputModel>;

/**
 * Represents the result of a field of study creation operation.
 */
export type CreationFieldStudyResult = Either<Error | Problem, FieldStudy>;

/**
 * Represents the result of a field of study update operation.
 */
export type DeleteFieldStudyResult = Either<Error | Problem, FieldStudy>;