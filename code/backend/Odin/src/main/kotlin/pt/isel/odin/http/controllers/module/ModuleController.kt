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
import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.module.models.SaveModuleInputModel
import pt.isel.odin.http.controllers.module.models.SaveModuleOutputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleInputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleOutputModel
import pt.isel.odin.http.controllers.module.models.getAllModulesOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.service.module.ModuleService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the Modules.
 */
@RestController
@RequestMapping("/api/modules")
class ModuleController(private val moduleService: ModuleService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = moduleService.getById(id)) {
            is Success -> ResponseEntity.ok(GetModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = moduleService.getAll()) {
            is Success -> ResponseEntity.ok(getAllModulesOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PostMapping("/save")
    fun save(@RequestBody moduleRequest: SaveModuleInputModel): ResponseEntity<*> =
        when (val result = moduleService.save(moduleRequest)) {
            is Success -> ResponseEntity.ok(SaveModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PutMapping("/update")
    fun update(@RequestBody moduleRequest: UpdateModuleInputModel): ResponseEntity<*> =
        when (val result = moduleService.update(moduleRequest)) {
            is Success -> ResponseEntity.ok(UpdateModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = moduleService.delete(id)) {
            is Success -> ResponseEntity.ok(GetModuleOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
