package pt.isel.odin.controller.dto.voc

import kotlinx.datetime.LocalDate

data class VocUpdateInputModel(
    val id: Long,
    val description: String?,
    val approved: Boolean?,
    val studentId: Long?,
    val curricularUnitId: Long?,
    val started: LocalDate?,
    val ended: LocalDate?
)
