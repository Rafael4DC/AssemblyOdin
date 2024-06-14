package pt.isel.odin.service.tech

import pt.isel.odin.model.Tech
import pt.isel.odin.service.tech.error.DeleteTechError
import pt.isel.odin.service.tech.error.GetTechError
import pt.isel.odin.service.tech.error.SaveUpdateTechError
import pt.isel.odin.utils.Either

/**
 * Represents the tech get result.
 *
 * @see GetTechError
 * @see Tech
 */
typealias GetTechResult = Either<GetTechError, Tech>

/**
 * Represents the tech get all results.
 *
 * @see GetTechError
 * @see Tech
 */
typealias GetAllTechsResult = Either<GetTechError, List<Tech>>

/**
 * Represents the tech creation result.
 *
 * @see SaveUpdateTechError
 * @see Tech
 */
typealias CreationTechResult = Either<SaveUpdateTechError, Tech>

/**
 * Represents the tech delete result.
 *
 * @see DeleteTechError
 * @see Tech
 */
typealias DeleteTechResult = Either<DeleteTechError, Tech>
