package pt.isel.odin.http.controllers.tech

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.tech.models.TechAttendanceResponse
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.model.Tech
import pt.isel.odin.service.tech.TechService
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the tech.
 */
@RestController
@RequestMapping("/api/techs")
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
     * @param techRequest the tech info to save without id.
     */
    @PostMapping("/save")
    fun save(@RequestBody techRequest: Tech, authentication: Principal): Tech {
        return techService.save(techRequest, authentication.toEmail())
    }

    /**
     * Updates a tech.
     *
     * @param techRequest the tech info to update with id.
     */
    @PutMapping("/update")
    fun update(@RequestBody techRequest: Tech): Tech {
        return techService.update(techRequest)
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

    /**
     * Gets all techs by student email.
     *
     * @param authentication the User.
     */
    @GetMapping("/user")
    fun getByUser(authentication: Principal): List<Tech> {
        return techService.getByUser(authentication.toEmail())
    }

    /**
     * Gets all techs and students by User email.
     *
     * @param authentication the User.
     */
    @GetMapping("/attendance")
    fun getMyTechsAttendance(authentication: Principal): List<TechAttendanceResponse> {
        return techService.getMyTechsAttendance(authentication.toEmail())
    }
}
