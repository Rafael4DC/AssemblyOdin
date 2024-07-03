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
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.tech.models.GetTechOutputModel
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.SaveTechOutputModel
import pt.isel.odin.http.controllers.tech.models.SaveScheduleTechInputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechInputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechOutputModel
import pt.isel.odin.http.controllers.tech.models.getAllTechsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.service.tech.TechService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the tech.
 */
@RestController
@RequestMapping(Uris.Techs.RESOURCE)
class TechController(private val techService: TechService) {

    /**
     * Gets a tech by its id.
     *
     * @param id the tech id.
     *
     * @return The tech.
     */
    @GetMapping(Uris.Techs.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = techService.getById(id)) {
            is Success -> responde(GetTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all techs.
     *
     * @return All techs.
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = techService.getAll()) {
            is Success -> responde(getAllTechsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a tech.
     * Teacher can be placed in the request body or in the authentication token.
     *
     * @param saveTechInputModel The tech to save.
     *
     * @return The saved tech id.
     */
    @PostMapping(Uris.Techs.SAVE)
    fun save(@RequestBody saveTechInputModel: SaveTechInputModel, authentication: Principal?): ResponseEntity<*> =
        when (val result = techService.save(saveTechInputModel, authentication.toEmail())) {
            is Success -> responde(
                SaveTechOutputModel(result.value),
                201,
                Uris.Techs.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves multiple techs.
     *
     * @param input The techs to save.
     * @param authentication The authentication token.
     *
     * @return The saved techs.
     */
    @PostMapping(Uris.Techs.SAVE_MULTIPLE)
    fun saveMultiple(@RequestBody input: SaveScheduleTechInputModel, authentication: Principal?): ResponseEntity<*> {
        val results = techService.saveMultipleClasses(input, authentication.toEmail())
        return if (results.all { it is Success }) {
            ResponseEntity.status(201).body(results.map { (it as Success).value })
        } else {
            Problem.responseForError(results.first { it is Failure })
        }
    }

    /**
     * Updates a tech.
     *
     * @param updateTechInputModel The tech to update.
     * @param authentication The authentication token.
     *
     * @return The updated tech.
     */
    @PutMapping(Uris.Techs.UPDATE)
    fun update(@RequestBody updateTechInputModel: UpdateTechInputModel, authentication: Principal?): ResponseEntity<*> =
        when (val result = techService.update(updateTechInputModel, authentication.toEmail())) {
            is Success -> responde(UpdateTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates multiple techs.
     *
     * @param input The techs to update.
     * @param authentication The authentication token.
     *
     * @return The updated techs.
     */
    @PostMapping(Uris.Techs.UPDATE_MULTIPLE)
    fun updateMultiple(@RequestBody input: SaveScheduleTechInputModel, authentication: Principal?): ResponseEntity<*> {
        val results = techService.updateMultipleClasses(input, authentication.toEmail())
        return if (results.all { it is Success }) {
            ResponseEntity.status(201).body(results.map { (it as Success).value })
        } else {
            Problem.responseForError(results.first { it is Failure })
        }
    }

    /**
     * Deletes a tech.
     *
     * @param id The tech id.
     *
     * @return The deleted tech.
     */
    @DeleteMapping(Uris.Techs.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = techService.delete(id)) {
            is Success -> responde(GetTechOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all techs by user.
     *
     * @param authentication The authentication token.
     *
     * @return All techs of a user.
     */
    @GetMapping(Uris.Techs.GET_BY_USER)
    fun getByUser(authentication: Principal): ResponseEntity<*> =
        when (val result = techService.getByUser(authentication.toEmail())) {
            is Success -> responde(getAllTechsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
