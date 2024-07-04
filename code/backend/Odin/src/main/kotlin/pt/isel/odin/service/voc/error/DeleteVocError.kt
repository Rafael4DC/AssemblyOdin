package pt.isel.odin.service.voc.error

/**
 * Represents the voc delete error.
 */
sealed class DeleteVocError {
    data object NotFoundVoc : DeleteVocError()
}
