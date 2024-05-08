package pt.isel.odin.controller.dto.student

import pt.isel.odin.model.Student

class StudentRequest(
    val id: Long?,
    val username: String?,
    val email: String?,
    val credits: Int?
)

fun StudentRequest.toStudent() = Student(
    /*id = id,*/
    email = email,
    username = username,
    credits = credits
)
