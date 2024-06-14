package pt.isel.odin.http.controllers.voc

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.voc.models.GetVocOutputModel
import pt.isel.odin.http.controllers.voc.models.SaveVocInputModel
import pt.isel.odin.http.controllers.voc.models.SaveVocOutputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocOutputModel
import pt.isel.odin.http.controllers.voc.models.getAllVocsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.service.voc.VocService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the voc.
 */
@RestController
@RequestMapping("/api/vocs")
class VocController(private val vocService: VocService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = vocService.getById(id)) {
            is Success -> ResponseEntity.ok(GetVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = vocService.getAll()) {
            is Success -> ResponseEntity.ok(getAllVocsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PostMapping("/save")
    fun save(@RequestBody vocRequest: SaveVocInputModel, authentication: Principal): ResponseEntity<*> =
        when (val result = vocService.save(vocRequest, authentication.toEmail())) {
            is Success -> ResponseEntity.ok(SaveVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PutMapping("/update")
    fun update(@RequestBody vocRequest: UpdateVocInputModel, authentication: Principal): ResponseEntity<*> =
        when (val result = vocService.update(vocRequest, authentication.toEmail())) {
            is Success -> ResponseEntity.ok(UpdateVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = vocService.delete(id)) {
            is Success -> ResponseEntity.ok(GetVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @GetMapping("/user")
    fun getByUser(authentication: Principal): ResponseEntity<*> =
        when (val result = vocService.getByUser(authentication.toEmail())) {
            is Success -> ResponseEntity.ok(getAllVocsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
