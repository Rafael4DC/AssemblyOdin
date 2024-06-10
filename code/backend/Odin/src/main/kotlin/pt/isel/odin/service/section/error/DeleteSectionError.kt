package pt.isel.odin.service.section.error

/**
 * Represents the section delete error.
 */
sealed class DeleteSectionError {
    data object NotFoundSection : DeleteSectionError()
}