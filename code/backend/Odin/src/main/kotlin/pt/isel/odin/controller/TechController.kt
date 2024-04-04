package pt.isel.odin.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.controller.dto.tech.TechSaveInputModel
import pt.isel.odin.controller.dto.tech.TechUpdateInputModel
import pt.isel.odin.model.Tech
import pt.isel.odin.service.interfaces.TechService

/**
 * Represents the controller that contains the endpoints related to the tech.
 */
@RestController
@RequestMapping("/techs")
class TechController(private val techService: TechService) {

    /**
     * Gets a tech by its id.
     *
     * @param id the tech id.
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Tech? {
        return techService.getById(id)
    }

    /**
     * Gets all techs.
     */
    @GetMapping
    fun getAll(): List<Tech> {
        return techService.getAll()
    }

    /**
     * Saves a tech.
     *
     * @param techSaveInputModel the tech info to save without id.
     */
    @PostMapping("/save")
    fun save(@RequestBody techSaveInputModel: TechSaveInputModel): Tech {
        return techService.save(techSaveInputModel)
    }

    /**
     * Updates a tech.
     *
     * @param techUpdateInputModel the tech info to update with id.
     */
    @PutMapping("/update")
    fun update(@RequestBody techUpdateInputModel: TechUpdateInputModel): Tech {
        return techService.update(techUpdateInputModel)
    }

    /**
     * Deletes a tech by its id.
     *
     * @param id the tech id.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        techService.delete(id)
    }
}
