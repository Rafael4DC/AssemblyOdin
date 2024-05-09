package pt.isel.odin.controller.dto.voc

import kotlinx.datetime.LocalDate
import pt.isel.odin.model.CurricularUnit
import pt.isel.odin.model.Student
import pt.isel.odin.model.Voc

data class VocRequest(
    val id: Long?,
    val description: String?,
    val approved: Boolean?,
    val studentEmail: String?,
    val curricularUnitId: Long?,
    val started: LocalDate?,
    val ended: LocalDate?
)

fun VocRequest.toVoc(): Voc {
    return Voc(
        id = this.id,
        description = this.description,
        approved = this.approved,
        student = Student(this.studentEmail),
        curricularUnit = CurricularUnit(this.curricularUnitId),
        started = this.started,
        ended = this.ended
    )
}
