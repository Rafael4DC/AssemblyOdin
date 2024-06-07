package pt.isel.odin.http.utils

import org.springframework.http.ResponseEntity
import java.net.URI

/**
 * Utility functions to create responses.
 */
fun <T> responde(response: T, status: Long = 200, uri: URI) = ResponseEntity
    .status(status.toInt())
    .header("Location", uri.toASCIIString())
    .body<Any>(response)

fun <T> responde(response: T, status: Int = 200) = ResponseEntity
    .status(status)
    .body<Any>(response)
