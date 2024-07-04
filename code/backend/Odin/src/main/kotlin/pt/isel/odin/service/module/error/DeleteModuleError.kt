package pt.isel.odin.service.module.error

/**
 * Represents the module delete error.
 */
sealed class DeleteModuleError {
    data object NotFoundModule : DeleteModuleError()
}
