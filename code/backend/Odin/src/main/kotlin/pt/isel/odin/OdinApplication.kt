package pt.isel.odin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Odin application.
 */
@SpringBootApplication
class OdinApplication

/**
 * Main function.
 */
fun main(args: Array<String>) {
    runApplication<OdinApplication>(*args)
}
