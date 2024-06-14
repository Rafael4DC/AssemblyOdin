package pt.isel.odin.service.section

import pt.isel.odin.model.Section
import pt.isel.odin.service.module.error.DeleteModuleError
import pt.isel.odin.service.section.error.DeleteSectionError
import pt.isel.odin.service.section.error.GetSectionError
import pt.isel.odin.service.section.error.SaveUpdateSectionError
import pt.isel.odin.utils.Either

/**
 * Represents the section get error.
 *
 * @see GetSectionError
 * @see Section
 */
typealias GetSectionResult = Either<GetSectionError, Section>

/**
 * Represents the section get all errors.
 *
 * @see GetSectionError
 * @see Section
 */
typealias GetAllSectionsResult = Either<GetSectionError, List<Section>>

/**
 * Represents the section creation error.
 *
 * @see SaveUpdateSectionError
 * @see Section
 */
typealias CreationSectionResult = Either<SaveUpdateSectionError, Section>

/**
 * Represents the section delete error.
 *
 * @see DeleteModuleError
 * @see Section
 */
typealias DeleteSectionResult = Either<DeleteSectionError, Section>
