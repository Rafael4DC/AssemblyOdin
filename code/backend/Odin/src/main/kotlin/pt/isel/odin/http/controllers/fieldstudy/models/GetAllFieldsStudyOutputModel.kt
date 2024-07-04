package pt.isel.odin.http.controllers.fieldstudy.models

import pt.isel.odin.model.FieldStudy

/**
 * Represents the output model for getting all fields study.
 *
 * @property fieldsStudy The fields study.
 */
data class GetAllFieldsStudyOutputModel(
    val fieldsStudy: List<GetFieldStudyOutputModel>
)

/**
 * Creates a [GetAllFieldsStudyOutputModel] from a list of [FieldStudy].
 *
 * @param fieldsStudy The fields study.
 *
 * @return The [GetAllFieldsStudyOutputModel].
 */
fun getAllFieldsStudyOutputModel(fieldsStudy: List<FieldStudy>): GetAllFieldsStudyOutputModel {
    return GetAllFieldsStudyOutputModel(
        fieldsStudy.map {
            GetFieldStudyOutputModel(it)
        }
    )
}
