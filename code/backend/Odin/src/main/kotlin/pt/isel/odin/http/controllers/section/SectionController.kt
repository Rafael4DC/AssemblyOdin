package pt.isel.odin.http.controllers.section

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.section.models.SaveSectionInputModel
import pt.isel.odin.http.controllers.section.models.SaveSectionOutputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionInputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionOutputModel
import pt.isel.odin.http.controllers.section.models.getAllSectionsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.service.section.SectionService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the Section.
 */
@RestController
@RequestMapping("/api/section")
class SectionController(private val sectionService: SectionService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = sectionService.getById(id)) {
            is Success -> ResponseEntity.ok(GetSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = sectionService.getAll()) {
            is Success -> ResponseEntity.ok(getAllSectionsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PostMapping("/save")
    fun save(@RequestBody sectionRequest: SaveSectionInputModel): ResponseEntity<*> =
        when (val result = sectionService.save(sectionRequest)) {
            is Success -> ResponseEntity.ok(SaveSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @PutMapping("/update")
    fun update(@RequestBody sectionRequest: UpdateSectionInputModel): ResponseEntity<*> =
        when (val result = sectionService.update(sectionRequest)) {
            is Success -> ResponseEntity.ok(UpdateSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = sectionService.delete(id)) {
            is Success -> ResponseEntity.ok(GetSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
