package pt.isel.odin.service.voc.error

/**
 * Represents the voc get error.
 */
sealed class GetVocError {
    data object NotFoundVoc : GetVocError()
    data object NotFoundUser : GetVocError()
}
