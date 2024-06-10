package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Voc

data class GetVocOutputModel(
    val id: Long,
    val description: String,
    val approved: Boolean,
    val user: GetUserOutputModel,
    val module: GetModuleOutputModel,
    val started: String,
    val ended: String
) {
    constructor(voc: Voc) : this(
        id = voc.id!!,
        description = voc.description,
        approved = voc.approved,
        user = GetUserOutputModel(voc.user),
        module = GetModuleOutputModel(voc.module),
        started = voc.started.toString(),
        ended = voc.ended.toString()
    )
}
