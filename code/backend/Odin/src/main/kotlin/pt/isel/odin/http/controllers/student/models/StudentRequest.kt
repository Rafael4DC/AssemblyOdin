package pt.isel.odin.http.controllers.student.models

import pt.isel.odin.model.Student

class StudentRequest(
    val id: Long? = null,
    val email: String?,
    val username: String?,
    val credits: Int? = 0,
    /*val role: String? = null*/
)

fun StudentRequest.toStudent() = Student(
    id = id,
    email = email,
    username = username,
    credits = credits,
    /*role = role.toRole()*/
)
