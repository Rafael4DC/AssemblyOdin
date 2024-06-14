package pt.isel.odin.service.fieldstudy

import pt.isel.odin.model.FieldStudy
import pt.isel.odin.service.fieldstudy.error.DeleteFieldStudyError
import pt.isel.odin.service.fieldstudy.error.GetFieldStudyError
import pt.isel.odin.service.fieldstudy.error.SaveUpdateFieldStudyError
import pt.isel.odin.utils.Either

/**
 * Represents the field of study get error.
 *
 * @see GetFieldStudyError
 * @see FieldStudy
 */
typealias GetFieldStudyResult = Either<GetFieldStudyError, FieldStudy>

/**
 * Represents the field of study get all errors.
 *
 * @see GetFieldStudyError
 * @see FieldStudy
 */
typealias GetAllFieldsStudyResult = Either<GetFieldStudyError, List<FieldStudy>>

/**
 * Represents the field of study creation error.
 *
 * @see SaveUpdateFieldStudyError
 * @see FieldStudy
 */
typealias CreationFieldStudyResult = Either<SaveUpdateFieldStudyError, FieldStudy>

/**
 * Represents the field of study delete error.
 *
 * @see DeleteFieldStudyError
 * @see FieldStudy
 */
typealias DeleteFieldStudyResult = Either<DeleteFieldStudyError, FieldStudy>
