package pt.isel.odin.service.section.error

/**
 * Represents the section creation error.
 */
sealed class SaveUpdateSectionError {
    data object AlreadyExistsSection : SaveUpdateSectionError()
    data object NotFoundFieldStudy : SaveUpdateSectionError()
    data object NotFoundSection : SaveUpdateSectionError()
}
