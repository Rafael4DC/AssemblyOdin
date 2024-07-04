package pt.isel.odin.http.controllers.module

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
import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.module.models.SaveModuleInputModel
import pt.isel.odin.http.controllers.module.models.SaveModuleOutputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleInputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleOutputModel
import pt.isel.odin.http.controllers.module.models.getAllModulesOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.service.module.ModuleService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the Modules.
 */
@RestController
@RequestMapping(Uris.Modules.RESOURCE)
class ModuleController(private val moduleService: ModuleService) {

    /**
     * Gets a module by its id.
     *
     * @param id the module id.
     *
     * @return The module.
     */
    @GetMapping(Uris.Modules.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = moduleService.getById(id)) {
            is Success -> responde(GetModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all modules.
     *
     * @return All modules.
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = moduleService.getAll()) {
            is Success -> responde(getAllModulesOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a new module.
     *
     * @param moduleRequest the module request.
     *
     * @return The saved module id.
     */
    @PostMapping(Uris.Modules.SAVE)
    fun save(@RequestBody moduleRequest: SaveModuleInputModel): ResponseEntity<*> =
        when (val result = moduleService.save(moduleRequest)) {
            is Success -> responde(
                SaveModuleOutputModel(result.value),
                201,
                Uris.Modules.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates a module.
     *
     * @param moduleRequest the module request.
     *
     * @return The updated module.
     */
    @PutMapping(Uris.Modules.UPDATE)
    fun update(@RequestBody moduleRequest: UpdateModuleInputModel): ResponseEntity<*> =
        when (val result = moduleService.update(moduleRequest)) {
            is Success -> responde(UpdateModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Deletes a module.
     *
     * @param id the module id.
     *
     * @return The deleted module.
     */
    @DeleteMapping(Uris.Modules.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = moduleService.delete(id)) {
            is Success -> responde(GetModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
