import {Problem} from "../_utils/Problem";
import {Either} from "../_utils/Either";
import {Voc} from "./models/Voc";
import {GetAllVocsOutputModel} from "./models/GetAllVocsOutputModel";

/**
 * Represents the result of a voc get operation.
 */
export type GetVocResult = Either<Error | Problem, Voc>;

/**
 * Represents the result of a voc get all operations.
 */
export type GetAllVocsResult = Either<Error | Problem, GetAllVocsOutputModel>;

/**
 * Represents the result of a voc creation operation.
 */
export type CreationVocResult = Either<Error | Problem, Voc>;

/**
 * Represents the result of a voc update operation.
 */
export type DeleteVocResult = Either<Error | Problem, Voc>;