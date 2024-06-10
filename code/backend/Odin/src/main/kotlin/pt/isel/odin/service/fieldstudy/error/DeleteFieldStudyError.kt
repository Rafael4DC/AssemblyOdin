package pt.isel.odin.service.fieldstudy.error

/**
 * Represents the field of study delete error.
 */
sealed class DeleteFieldStudyError {
    data object NotFoundFieldStudy : DeleteFieldStudyError()
}