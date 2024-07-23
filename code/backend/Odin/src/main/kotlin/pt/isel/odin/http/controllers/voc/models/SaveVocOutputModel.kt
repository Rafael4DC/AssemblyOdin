package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.model.Voc

/**
 * Represents the output model for saving a voc.
 *
 * @property id The voc id.
 *
 * @constructor Creates a [SaveVocOutputModel] from a [Voc].
 */
data class SaveVocOutputModel(
    val id: Long
) {
    constructor(voc: Voc) : this(
        id = voc.id!!
    )
}
