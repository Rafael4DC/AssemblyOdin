package pt.isel.odin.service.voc.error

/**
 * Represents the voc creation error.
 */
sealed class SaveUpdateVocError {
    data object NotFoundUser : SaveUpdateVocError()
    data object NotFoundSection : SaveUpdateVocError()
    data object NotFoundFieldStudy : SaveUpdateVocError()
    data object NotFoundVoc : SaveUpdateVocError()
}
