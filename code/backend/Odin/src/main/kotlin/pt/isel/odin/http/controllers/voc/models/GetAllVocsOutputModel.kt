package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.model.Voc

data class GetAllVocsOutputModel(
    val vocs: List<GetVocOutputModel>
)

fun getAllVocsOutputModel(vocs: List<Voc>): GetAllVocsOutputModel {
    return GetAllVocsOutputModel(
        vocs.map {
            GetVocOutputModel(it)
        }
    )
}