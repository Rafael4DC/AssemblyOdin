package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.model.Voc

/**
 * Represents the output model for getting a voc.
 *
 * @property id The voc id.
 * @property description The voc description.
 * @property approved The voc approved.
 * @property user The voc user.
 * @property section The voc section.
 * @property started The voc started.
 * @property ended The voc ended.
 *
 * @constructor Creates a [GetVocOutputModel] from a [Voc].
 */
data class GetVocOutputModel(
    val id: Long,
    val description: String,
    val approved: Boolean,
    val user: GetUserOutputModel,
    val section: GetSectionOutputModel,
    val started: String,
    val ended: String
) {
    constructor(voc: Voc) : this(
        id = voc.id!!,
        description = voc.description,
        approved = voc.approved,
        user = GetUserOutputModel(voc.user),
        section = GetSectionOutputModel(voc.section),
        started = voc.started.toString(),
        ended = voc.ended.toString()
    )
}
