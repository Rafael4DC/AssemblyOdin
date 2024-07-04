package pt.isel.odin.http.utils

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import java.security.Principal

fun Principal?.toEmail(): String = this?.let {
    (it as OAuth2AuthenticationToken).principal.attributes["email"].toString()
} ?: "invalid@gmail.com"

fun Principal.toName(): String =
    (this as OAuth2AuthenticationToken).principal.attributes["name"].toString()

fun isStudent(email: String): Boolean =
    email.endsWith("@students.assembly.pt") || email.endsWith("@alunos.isel.pt")
