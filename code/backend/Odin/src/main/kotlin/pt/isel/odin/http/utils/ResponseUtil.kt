package pt.isel.odin.http.utils

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import java.net.URI

/**
 * Creates a response entity with the given response, status and uri.
 *
 * @param response The response.
 * @param status The status.
 * @param uri The uri.
 *
 * @return The response entity.
 */
fun <T> responde(response: T, status: Int = 200, uri: URI? = null): ResponseEntity<Any> {
    val headers = HttpHeaders().apply {
        add(HttpHeaders.CONTENT_TYPE, "application/json")
        uri?.let { add(HttpHeaders.LOCATION, it.toASCIIString()) }
    }

    return ResponseEntity
        .status(status)
        .headers(headers)
        .body(response)
}
