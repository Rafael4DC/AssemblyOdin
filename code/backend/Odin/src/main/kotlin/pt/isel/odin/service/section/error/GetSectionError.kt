package pt.isel.odin.service.section.error

/**
 * Represents the section get error.
 */
sealed class GetSectionError {
    data object NotFoundSection : GetSectionError()
}