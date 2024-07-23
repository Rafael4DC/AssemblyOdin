package pt.isel.odin.http.controllers.fieldstudy.models

import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.model.FieldStudy

/**
 * Represents the output model for getting a field study.
 *
 * @property id The field study id.
 * @property name The field study name.
 * @property modules The field study modules.
 *
 * @constructor Creates a [GetFieldStudyOutputModel] from a [FieldStudy].
 */
data class GetFieldStudyOutputModel(
    val id: Long,
    val name: String,
    val modules: List<GetModuleOutputModel>
) {
    constructor(fieldStudy: FieldStudy) : this(
        id = fieldStudy.id!!,
        name = fieldStudy.name,
        modules = fieldStudy.modules
            .map { GetModuleOutputModel(it) }
    )
}
