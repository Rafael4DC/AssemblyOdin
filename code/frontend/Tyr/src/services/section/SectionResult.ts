import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {Section} from "./models/Section";
import {GetAllSectionsOutputModel} from "./models/GetAllSectionsOutputModel";

/**
 * Represents the result of a section get operation.
 */
export type GetSectionResult = Either<Error | Problem, Section>;

/**
 * Represents the result of a section get all operations.
 */
export type GetAllSectionsResult = Either<Error | Problem, GetAllSectionsOutputModel>;

/**
 * Represents the result of a section creation operation.
 */
export type CreationSectionResult = Either<Error | Problem, Section>;

/**
 * Represents the result of a section update operation.
 */
export type DeleteSectionResult = Either<Error | Problem, Section>;