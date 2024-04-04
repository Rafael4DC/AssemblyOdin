package pt.isel.odin.controller.dto.classattendance

data class ClassAttendUpdateInputModel(
    val id: Long,
    val studentId: Long?,
    val techId: Long?,
    val attended: Boolean?
)
