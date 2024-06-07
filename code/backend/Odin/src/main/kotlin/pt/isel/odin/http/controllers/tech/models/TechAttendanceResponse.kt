package pt.isel.odin.http.controllers.tech.models

import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.Tech

data class TechAttendanceResponse(
    val tech: Tech,
    val attendedStudents: List<ClassAttendance>
)
