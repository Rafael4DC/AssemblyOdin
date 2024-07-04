package pt.isel.odin.http.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.utils.responde

/**
 * Represents the controller that contains the endpoints related to the home.
 */
@RestController
@RequestMapping(Uris.HOME)
class HomeController {

    /**
     * Gets the home page.
     *
     * @return The home page.
     */
    @GetMapping
    fun home(): ResponseEntity<*> {
        val response = mapOf(
            "message" to "Welcome to the API",
            "api_version" to "1.0.0",
            "documentation" to "https://yourapi.docs"
        )
        return responde(response)
    }
}
