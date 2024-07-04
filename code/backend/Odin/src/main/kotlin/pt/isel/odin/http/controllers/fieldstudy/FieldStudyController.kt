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
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.fieldstudy.models.GetFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.getAllFieldsStudyOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.service.fieldstudy.FieldStudyService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the Field Study.
 */
@RestController
@RequestMapping(Uris.FieldsStudy.RESOURCE)
class FieldStudyController(private val fieldStudyService: FieldStudyService) {

    /**
     * Gets a field study by its id.
     *
     * @param id the field study id.
     *
     * @return The [FieldStudy].
     */
    @GetMapping(Uris.FieldsStudy.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = fieldStudyService.getById(id)) {
            is Success -> responde(GetFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all fields study.
     *
     * @return All [FieldStudy].
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = fieldStudyService.getAll()) {
            is Success -> responde(getAllFieldsStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a new field study.
     *
     * @param fieldStudyRequest the field study to save.
     *
     * @return The saved [FieldStudy] id.
     */
    @PostMapping(Uris.FieldsStudy.SAVE)
    fun save(@RequestBody fieldStudyRequest: SaveFieldStudyInputModel): ResponseEntity<*> =
        when (val result = fieldStudyService.save(fieldStudyRequest)) {
            is Success -> responde(
                SaveFieldStudyOutputModel(result.value),
                201,
                Uris.FieldsStudy.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates a field study.
     *
     * @param fieldStudyRequest the field study to update.
     *
     * @return The updated [FieldStudy].
     */
    @PutMapping(Uris.FieldsStudy.UPDATE)
    fun update(@RequestBody fieldStudyRequest: UpdateFieldStudyInputModel): ResponseEntity<*> =
        when (val result = fieldStudyService.update(fieldStudyRequest)) {
            is Success -> responde(UpdateFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Deletes a field study.
     *
     * @param id the field study id.
     *
     * @return The [FieldStudy].
     */
    @DeleteMapping(Uris.FieldsStudy.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = fieldStudyService.delete(id)) {
            is Success -> responde(GetFieldStudyOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
