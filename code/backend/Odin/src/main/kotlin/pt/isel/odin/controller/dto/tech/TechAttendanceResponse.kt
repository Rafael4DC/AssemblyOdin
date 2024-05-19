package pt.isel.odin.controller.dto.tech

import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.Tech

data class TechAttendanceResponse(
    val tech: Tech,
    val attendedStudents: List<ClassAttendance>
)
