package pt.isel.odin.http.controllers.fieldstudy

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.fieldstudy.models.GetFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.getAllFieldsStudyOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.service.fieldstudy.FieldStudyService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the Field Study.
 */
@RestController
@RequestMapping("/api/fieldstudy")
class FieldStudyController(private val fieldStudyService: FieldStudyService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = fieldStudyService.getById(id)) {
            is Success -> ResponseEntity.ok(GetFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = fieldStudyService.getAll()) {
            is Success -> ResponseEntity.ok(getAllFieldsStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PostMapping("/save")
    fun save(@RequestBody fieldStudyRequest: SaveFieldStudyInputModel): ResponseEntity<*> =
        when (val result = fieldStudyService.save(fieldStudyRequest)) {
            is Success -> ResponseEntity.ok(SaveFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PutMapping("/update")
    fun update(@RequestBody fieldStudyRequest: UpdateFieldStudyInputModel): ResponseEntity<*> =
        when (val result = fieldStudyService.update(fieldStudyRequest)) {
            is Success -> ResponseEntity.ok(UpdateFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = fieldStudyService.delete(id)) {
            is Success -> ResponseEntity.ok(GetFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
