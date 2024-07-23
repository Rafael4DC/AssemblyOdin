package pt.isel.odin.service.fieldstudy.error

/**
 * Represents the field of study creation error.
 */
sealed class SaveUpdateFieldStudyError {
    data object AlreadyExistsFieldStudy : SaveUpdateFieldStudyError()
    data object NotFoundDepartment : SaveUpdateFieldStudyError()
    data object NotFoundFieldStudy : SaveUpdateFieldStudyError()
    data object IncorrectNameFieldStudy : SaveUpdateFieldStudyError()
}
