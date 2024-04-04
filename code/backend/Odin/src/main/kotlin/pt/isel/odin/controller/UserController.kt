package pt.isel.odin.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.model.User
import pt.isel.odin.service.interfaces.UserService

/**
 * Represents the controller that contains the endpoints related to the user.
 */
@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    /**
     * Gets a user by its id.
     *
     * @param id the user id.
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): User? {
        return userService.getById(id)
    }

    /**
     * Gets all users.
     */
    @GetMapping
    fun getAll(): List<User> {
        return userService.getAll()
    }

    /**
     * Saves a user.
     *
     * @param user the user to save.
     */
    @PostMapping("/save")
    fun save(@RequestBody user: User): User {
        return userService.save(user)
    }

    /**
     * Updates a user.
     *
     * @param user the user to update.
     */
    @PutMapping("/update")
    fun update(@RequestBody user: User): User {
        return userService.save(user)
    }

    /**
     * Deletes a user by its id.
     *
     * @param id the user id.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        userService.delete(id)
    }
}
