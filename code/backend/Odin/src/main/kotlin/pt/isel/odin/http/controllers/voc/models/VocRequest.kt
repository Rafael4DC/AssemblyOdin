package pt.isel.odin.http.controllers.voc.models

import pt.isel.odin.model.Module
import pt.isel.odin.model.Student
import pt.isel.odin.model.Voc
import java.time.LocalDateTime

data class VocRequest(
    val id: Long?,
    val description: String?,
    val approved: Boolean?,
    val studentId: Long?,
    val moduleId: Long?,
    val started: LocalDateTime?,
    val ended: LocalDateTime?
){
    fun toVoc(): Voc {
        return Voc(
            id = this.id,
            description = this.description,
            approved = this.approved,
            student = Student(this.studentId),
            module = Module(this.moduleId),
            started = this.started,
            ended = this.ended
        )
    }
}
