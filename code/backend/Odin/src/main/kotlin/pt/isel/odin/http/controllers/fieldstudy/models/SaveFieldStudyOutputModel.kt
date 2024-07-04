package pt.isel.odin.http.controllers.fieldstudy.models

import pt.isel.odin.model.FieldStudy

/**
 * Represents the output model for saving a field study.
 *
 * @property id The field study id.
 *
 * @constructor Creates a [SaveFieldStudyOutputModel] from a [FieldStudy].
 */
data class SaveFieldStudyOutputModel(
    val id: Long
) {
    constructor(fieldStudy: FieldStudy) : this(
        id = fieldStudy.id!!
    )
}
