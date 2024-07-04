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
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.section.models.SaveSectionInputModel
import pt.isel.odin.http.controllers.section.models.SaveSectionOutputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionInputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionOutputModel
import pt.isel.odin.http.controllers.section.models.getAllSectionsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.service.section.SectionService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the Section.
 */
@RestController
@RequestMapping(Uris.Sections.RESOURCE)
class SectionController(private val sectionService: SectionService) {

    /**
     * Gets a section by its id.
     *
     * @param id the section id.
     *
     * @return The section.
     */
    @GetMapping(Uris.Sections.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = sectionService.getById(id)) {
            is Success -> responde(GetSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all sections.
     *
     * @return All sections.
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = sectionService.getAll()) {
            is Success -> responde(getAllSectionsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a new section.
     *
     * @param sectionRequest The section to save.
     *
     * @return The saved section id.
     */
    @PostMapping(Uris.Sections.SAVE)
    fun save(@RequestBody sectionRequest: SaveSectionInputModel): ResponseEntity<*> =
        when (val result = sectionService.save(sectionRequest)) {
            is Success -> responde(
                SaveSectionOutputModel(result.value),
                201,
                Uris.Sections.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates a section.
     *
     * @param sectionRequest The section to update.
     *
     * @return The updated section.
     */
    @PutMapping(Uris.Sections.UPDATE)
    fun update(@RequestBody sectionRequest: UpdateSectionInputModel): ResponseEntity<*> =
        when (val result = sectionService.update(sectionRequest)) {
            is Success -> responde(UpdateSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Deletes a section.
     *
     * @param id the section id.
     *
     * @return The section.
     */
    @DeleteMapping(Uris.Sections.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = sectionService.delete(id)) {
            is Success -> responde(GetSectionOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
