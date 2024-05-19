package pt.isel.odin.controller.dto.student

import pt.isel.odin.model.Student

class StudentRequest(
    val id: Long? = null,
    val email: String?,
    val username: String?,
    val credits: Int? = 0
)

fun StudentRequest.toStudent() = Student(
    id = id,
    email = email,
    username = username,
    credits = credits
)
