package pt.isel.odin.service.voc

import pt.isel.odin.model.Voc
import pt.isel.odin.service.voc.error.DeleteVocError
import pt.isel.odin.service.voc.error.GetVocError
import pt.isel.odin.service.voc.error.SaveUpdateVocError
import pt.isel.odin.utils.Either

/**
 * Represents the voc get result.
 *
 * @see GetVocError
 * @see Voc
 */
typealias GetVocResult = Either<GetVocError, Voc>

/**
 * Represents the voc get all results.
 *
 * @see GetVocError
 * @see Voc
 */
typealias GetAllVocsResult = Either<GetVocError, List<Voc>>

/**
 * Represents the voc creation result.
 *
 * @see SaveUpdateVocError
 * @see Voc
 */
typealias CreationVocResult = Either<SaveUpdateVocError, Voc>

/**
 * Represents the voc delete result.
 *
 * @see DeleteVocError
 * @see Voc
 */
typealias DeleteVocResult = Either<DeleteVocError, Voc>
