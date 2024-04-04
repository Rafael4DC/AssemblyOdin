package pt.isel.odin.controller.dto.classattendance

data class ClassAttendSaveInputModel(
    val studentId: Long,
    val techId: Long,
    val attended: Boolean
)
