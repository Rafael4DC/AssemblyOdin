package pt.isel.odin.controller.dto.tech

import kotlinx.datetime.LocalDate
import pt.isel.odin.model.CurricularUnit
import pt.isel.odin.model.Tech
import pt.isel.odin.model.User

data class TechRequest(
    val id: Long?,
    val teacherEmail: String?,
    val curricularUnitId: Long?,
    val date: LocalDate?,
    val summary: String?
)

fun TechRequest.toTech(): Tech {
    return Tech(
        id = this.id,
        teacher = User(this.teacherEmail),
        curricularUnit = CurricularUnit(this.curricularUnitId),
        date = this.date,
        summary = this.summary
    )
}
