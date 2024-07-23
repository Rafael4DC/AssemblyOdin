package pt.isel.odin.service.fieldstudy.error

/**
 * Represents the field of study get error.
 */
sealed class GetFieldStudyError {
    data object NotFoundFieldStudy : GetFieldStudyError()
}
