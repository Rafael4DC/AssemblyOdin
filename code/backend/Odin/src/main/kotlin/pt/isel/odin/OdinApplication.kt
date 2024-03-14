package pt.isel.odin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OdinApplication


fun main(args: Array<String>) {
    runApplication<OdinApplication>(*args)
}
