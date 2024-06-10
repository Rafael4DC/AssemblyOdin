package pt.isel.odin.http.controllers.tech

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.tech.models.GetTechOutputModel
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.SaveTechOutputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechInputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechOutputModel
import pt.isel.odin.http.controllers.tech.models.getAllTechsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.service.tech.TechService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the tech.
 */
@RestController
@RequestMapping("/api/techs")
class TechController(private val techService: TechService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = techService.getById(id)) {
            is Success -> ResponseEntity.ok(GetTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = techService.getAll()) {
            is Success -> ResponseEntity.ok(getAllTechsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PostMapping("/save")
    fun save(@RequestBody saveTechInputModel: SaveTechInputModel, authentication: Principal): ResponseEntity<*> =
        when (val result = techService.save(saveTechInputModel, authentication.toEmail())) {
            is Success -> ResponseEntity.ok(SaveTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PutMapping("/update")
    fun update(@RequestBody updateTechInputModel: UpdateTechInputModel, authentication: Principal): ResponseEntity<*> =
        when (val result = techService.update(updateTechInputModel, authentication.toEmail())) {
            is Success -> ResponseEntity.ok(UpdateTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = techService.delete(id)) {
            is Success -> ResponseEntity.ok(GetTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /*@GetMapping("/user")
    fun getByUser(authentication: Principal): ResponseEntity<*> =
        when (val result = techService.getByUser(authentication.toEmail())) {
            is Success -> ResponseEntity.ok(GetTechsByUserOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
    */
    /**
     * Gets all techs and students by User email.
     *
     * @param authentication the User.
     *//*
    @GetMapping("/attendance")
    fun getMyTechsAttendance(authentication: Principal): List<TechAttendanceResponse> {
        return techService.getMyTechsAttendance(authentication.toEmail())
    }*/

}
