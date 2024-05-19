package pt.isel.odin.controller.dto.tech

import pt.isel.odin.model.Tech
import pt.isel.odin.model.User
import java.time.LocalDateTime

data class TechRequest(
    val id: Long?,
    val teacherId: Long?,
    val moduleId: Long?,
    val date: LocalDateTime?,
    val summary: String?
)

fun TechRequest.toTech(): Tech {
    return Tech(
        id = this.id,
        teacher = User(this.teacherId),
        module = pt.isel.odin.model.Module(this.moduleId),
        date = this.date,
        summary = this.summary
    )
}
