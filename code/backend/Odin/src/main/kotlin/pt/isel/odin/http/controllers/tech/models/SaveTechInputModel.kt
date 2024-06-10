package pt.isel.odin.http.controllers.tech.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import pt.isel.odin.model.Module
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

data class SaveTechInputModel(
    val teacher: Long,

    val module: Long,

    val date: String,

    @NotBlank(message = "Summary is required")
    @Size(min = 1, max = 50, message = "Summary must have between 1 and 50 characters")
    val summary: String,

    val missTech: List<Long> = emptyList()
) {
    fun toTech(
        teacher: User,
        module: Module,
        missTech: MutableList<User>
    ) =
        Tech(
            teacher = teacher,
            module = module,
            date = LocalDateTime.parse(date),
            summary = summary,
            missTech = missTech
        )
}