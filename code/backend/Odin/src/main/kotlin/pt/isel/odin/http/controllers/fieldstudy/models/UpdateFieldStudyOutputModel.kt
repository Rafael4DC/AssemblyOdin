package pt.isel.odin.http.controllers.fieldstudy.models

import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.model.FieldStudy

data class UpdateFieldStudyOutputModel(
    val id: Long,
    val name: String,
    /*val modules: List<GetModuleOutputModel>*/
) {
    constructor(fieldStudy: FieldStudy) : this(
        id = fieldStudy.id!!,
        name = fieldStudy.name,
        /*modules = fieldStudy.modules!!
            .map { GetModuleOutputModel(it) }*/
    )
}