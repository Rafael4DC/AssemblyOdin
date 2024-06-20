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
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.voc.models.GetVocOutputModel
import pt.isel.odin.http.controllers.voc.models.SaveVocInputModel
import pt.isel.odin.http.controllers.voc.models.SaveVocOutputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocOutputModel
import pt.isel.odin.http.controllers.voc.models.getAllVocsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.service.voc.VocService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the voc.
 */
@RestController
@RequestMapping(Uris.Vocs.RESOURCE)
class VocController(private val vocService: VocService) {

    /**
     * Gets a voc by its id.
     *
     * @param id the voc id.
     *
     * @return The voc.
     */
    @GetMapping(Uris.Vocs.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = vocService.getById(id)) {
            is Success -> responde(GetVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all vocs.
     *
     * @return All vocs.
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = vocService.getAll()) {
            is Success -> responde(getAllVocsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a voc.
     * User can be placed in the request body or in the authentication token.
     *
     * @param vocRequest The voc to save.
     * @param authentication The authentication token.
     *
     * @return The saved voc id.
     */
    @PostMapping(Uris.Vocs.SAVE)
    fun save(@RequestBody vocRequest: SaveVocInputModel, authentication: Principal?): ResponseEntity<*> =
        when (val result = vocService.save(vocRequest, authentication.toEmail())) {
            is Success -> responde(
                SaveVocOutputModel(result.value),
                201,
                Uris.Vocs.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates a voc.
     * User can be placed in the request body or in the authentication token.
     *
     * @param vocRequest The voc to update.
     * @param authentication The authentication token.
     *
     * @return The updated voc.
     */
    @PutMapping(Uris.Vocs.UPDATE)
    fun update(@RequestBody vocRequest: UpdateVocInputModel, authentication: Principal?): ResponseEntity<*> =
        when (val result = vocService.update(vocRequest, authentication.toEmail())) {
            is Success -> responde(UpdateVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Deletes a voc.
     *
     * @param id The voc id.
     *
     * @return The deleted voc.
     */
    @DeleteMapping(Uris.Vocs.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = vocService.delete(id)) {
            is Success -> responde(GetVocOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all vocs of a user.
     *
     * @param authentication The authentication token.
     *
     * @return All vocs of the user.
     */
    @GetMapping(Uris.Vocs.GET_BY_USER)
    fun getByUser(authentication: Principal): ResponseEntity<*> =
        when (val result = vocService.getByUser(authentication.toEmail())) {
            is Success -> responde(getAllVocsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
