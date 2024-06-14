package pt.isel.odin.service.tech.error

/**
 * Represents the tech delete error.
 */
sealed class DeleteTechError {
    data object NotFoundTech : DeleteTechError()
}
