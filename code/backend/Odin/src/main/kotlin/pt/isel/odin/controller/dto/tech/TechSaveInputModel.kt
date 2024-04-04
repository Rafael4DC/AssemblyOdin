package pt.isel.odin.controller.dto.tech

import kotlinx.datetime.LocalDate

data class TechSaveInputModel(
    val teacherId: Long,
    val curricularUnitId: Long,
    val date: LocalDate,
    val summary: String
)
