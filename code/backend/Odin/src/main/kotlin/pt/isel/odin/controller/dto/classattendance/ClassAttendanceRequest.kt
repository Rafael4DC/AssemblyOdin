package pt.isel.odin.controller.dto.classattendance

import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.Student
import pt.isel.odin.model.Tech

data class ClassAttendanceRequest(
    val id: Long?,
    val studentEmail: String?,
    val techId: Long?,
    val attended: Boolean?
)

fun ClassAttendanceRequest.toClassAttendance(): ClassAttendance {
    return ClassAttendance(
        id = this.id,
        student = Student(this.studentEmail),
        tech = Tech(this.techId),
        attended = this.attended
    )
}
