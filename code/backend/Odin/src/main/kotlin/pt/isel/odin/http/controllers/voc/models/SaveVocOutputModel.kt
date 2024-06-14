package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.model.Voc

data class SaveVocOutputModel(
    val id: Long
) {
    constructor(voc: Voc) : this(
        id = voc.id!!
    )
}
