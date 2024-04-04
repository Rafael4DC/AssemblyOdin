package pt.isel.odin.controller.dto.tech

import kotlinx.datetime.LocalDate

data class TechUpdateInputModel(
    val id: Long,
    val teacherId: Long?,
    val curricularUnitId: Long?,
    val date: LocalDate?,
    val summary: String?
)
