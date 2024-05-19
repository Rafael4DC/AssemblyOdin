package pt.isel.odin.controller.dto.classattendance

import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.Student
import pt.isel.odin.model.Tech

data class ClassAttendanceRequest(
    val id: Long,
    val student: Student,
    val tech: Tech,
    val attended: Boolean
)

fun ClassAttendanceRequest.toClassAttendance(): ClassAttendance {
    return ClassAttendance(
        id = this.id,
        student = student,
        tech = tech,
        attended = this.attended
    )
}
