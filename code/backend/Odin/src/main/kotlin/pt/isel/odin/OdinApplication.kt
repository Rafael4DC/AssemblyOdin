package pt.isel.odin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class OdinApplication

fun main(args: Array<String>) {
    runApplication<OdinApplication>(*args)
}


@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:1337")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(3600)
    }
}