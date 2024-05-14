package pt.isel.odin.controller.dto

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import pt.isel.odin.model.User
import java.security.Principal

fun Principal.toUser(): User =
    (this as OAuth2AuthenticationToken).let {
        val attributes = it.principal.attributes
        return@let User(email = attributes["email"].toString(), username = attributes["name"].toString())
    }

fun Principal.toEmail(): String =
    (this as OAuth2AuthenticationToken).principal.attributes["email"].toString()

fun Principal.toName(): String =
    (this as OAuth2AuthenticationToken).principal.attributes["name"].toString()

fun isStudent(email: String): Boolean =
    email.endsWith("@students.assembly.pt") || email.endsWith("@alunos.isel.pt")