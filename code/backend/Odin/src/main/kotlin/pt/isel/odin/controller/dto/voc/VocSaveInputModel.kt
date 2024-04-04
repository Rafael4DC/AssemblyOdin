package pt.isel.odin.controller.dto.voc

import kotlinx.datetime.LocalDate

data class VocSaveInputModel(
    val description: String,
    val approved: Boolean,
    val studentId: Long,
    val curricularUnitId: Long,
    val started: LocalDate,
    val ended: LocalDate
)
