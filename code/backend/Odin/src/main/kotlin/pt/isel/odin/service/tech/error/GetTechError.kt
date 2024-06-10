package pt.isel.odin.service.tech.error

/**
 * Represents the tech get error.
 */
sealed class GetTechError {
    data object NotFoundTech : GetTechError()
}