package pt.isel.odin.service.module

import pt.isel.odin.model.Module
import pt.isel.odin.service.module.error.DeleteModuleError
import pt.isel.odin.service.module.error.GetModuleError
import pt.isel.odin.service.module.error.SaveUpdateModuleError
import pt.isel.odin.utils.Either

/**
 * Represents the module get error.
 *
 * @see GetModuleError
 * @see Module
 */
typealias GetModuleResult = Either<GetModuleError, Module>

/**
 * Represents the module get all errors.
 *
 * @see GetModuleError
 * @see Module
 */
typealias GetAllModulesResult = Either<GetModuleError, List<Module>>

/**
 * Represents the module creation error.
 *
 * @see SaveUpdateModuleError
 * @see Module
 */
typealias CreationModuleResult = Either<SaveUpdateModuleError, Module>

/**
 * Represents the module delete error.
 *
 * @see DeleteModuleError
 * @see Module
 */
typealias DeleteModuleResult = Either<DeleteModuleError, Module>
