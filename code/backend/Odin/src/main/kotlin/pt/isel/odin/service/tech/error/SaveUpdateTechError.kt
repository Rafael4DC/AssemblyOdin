package pt.isel.odin.service.tech.error

/**
 * Represents the tech creation error.
 */
sealed class SaveUpdateTechError {
    data object NotFoundFieldStudy : SaveUpdateTechError()
    data object NotFoundTech : SaveUpdateTechError()
    data object NotFoundUser : SaveUpdateTechError()
    data object NotFoundModule : SaveUpdateTechError()
}
