package pt.isel.odin.controller.dto.student

import pt.isel.odin.model.Student

class StudentRequest(
    val email: String?,
    val username: String?,
    val credits: Int? = 0
)

fun StudentRequest.toStudent() = Student(
    email = email,
    username = username,
    credits = credits
)
