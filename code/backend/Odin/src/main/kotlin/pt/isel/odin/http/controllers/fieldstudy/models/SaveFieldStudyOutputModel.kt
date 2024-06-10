package pt.isel.odin.http.controllers.fieldstudy.models

import pt.isel.odin.model.FieldStudy

data class SaveFieldStudyOutputModel(
    val id: Long
) {
    constructor(fieldStudy: FieldStudy) : this(
        id = fieldStudy.id!!
    )
}