package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.model.Voc

/**
 * Represents the output model for getting all vocs.
 *
 * @property vocs The vocs.
 *
 * @constructor Creates a [GetAllVocsOutputModel] from a list of [Voc].
 */
data class GetAllVocsOutputModel(
    val vocs: List<GetVocOutputModel>
)

/**
 * Creates a [GetAllVocsOutputModel] from a list of [Voc].
 *
 * @param vocs The vocs.
 *
 * @return The [GetAllVocsOutputModel].
 */
fun getAllVocsOutputModel(vocs: List<Voc>): GetAllVocsOutputModel {
    return GetAllVocsOutputModel(
        vocs.map {
            GetVocOutputModel(it)
        }
    )
}
