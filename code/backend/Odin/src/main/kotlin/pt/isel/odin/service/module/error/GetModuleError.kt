package pt.isel.odin.service.module.error

/**
 * Represents the module get error.
 */
sealed class GetModuleError {
    data object NotFoundModule : GetModuleError()
}
