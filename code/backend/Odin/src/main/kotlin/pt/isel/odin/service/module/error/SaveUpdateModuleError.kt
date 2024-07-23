package pt.isel.odin.service.module.error

/**
 * Represents the module creation error.
 */
sealed class SaveUpdateModuleError {
    data object AlreadyExistsModule : SaveUpdateModuleError()
    data object NotFoundFieldStudy : SaveUpdateModuleError()
    data object NotFoundModule : SaveUpdateModuleError()
    data object IncorrectNameModule : SaveUpdateModuleError()
}
