package pt.isel.odin.http.controllers.fieldstudy.models

import pt.isel.odin.model.FieldStudy

data class GetAllFieldsStudyOutputModel(
    val fieldsStudy: List<GetFieldStudyOutputModel>
)

fun getAllFieldsStudyOutputModel(fieldsStudy: List<FieldStudy>): GetAllFieldsStudyOutputModel {
    return GetAllFieldsStudyOutputModel(
        fieldsStudy.map {
            GetFieldStudyOutputModel(it)
        }
    )
}
