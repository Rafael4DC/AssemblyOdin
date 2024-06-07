package pt.isel.odin.http.controllers.voc

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.http.controllers.voc.models.VocRequest
import pt.isel.odin.model.Voc
import pt.isel.odin.service.voc.VocService
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the voc.
 */
@RestController
@RequestMapping("/api/vocs")
class VocController(private val vocService: VocService) {

    /**
     * Gets a voc by its id.
     *
     * @param id the voc id.
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Voc? {
        return vocService.getById(id)
    }

    /**
     * Gets all vocs.
     */
    @GetMapping
    fun getAll(): List<Voc> {
        return vocService.getAll()
    }

    /**
     * Saves a voc.
     *
     * @param vocRequest the voc info to save.
     */
    @PostMapping("/save")
    fun save(@RequestBody vocRequest: Voc, authentication: Principal): Voc {
        return vocService.save(vocRequest, authentication.toEmail())
    }

    /**
     * Updates a voc.
     *
     * @param vocRequest the voc info to update.
     */
    @PutMapping("/update")
    fun update(@RequestBody vocRequest: VocRequest): Voc {
        return vocService.update(vocRequest)
    }

    /**
     * Deletes a voc by its id.
     *
     * @param id the voc id.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        vocService.delete(id)
    }

    /**
     * Gets all vocs by User.
     *
     */
    @GetMapping("/student")
    fun getByStudent(authentication: Principal): List<Voc> {
        return vocService.getByStudent(authentication.toEmail())
    }
}
