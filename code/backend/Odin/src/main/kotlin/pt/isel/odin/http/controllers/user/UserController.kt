package pt.isel.odin.http.controllers.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.http.controllers.user.models.SaveUserInputModel
import pt.isel.odin.http.controllers.user.models.SaveUserOutputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserInputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserOutputModel
import pt.isel.odin.http.controllers.user.models.getAllUsersOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.http.utils.toEmail
import pt.isel.odin.service.user.UserService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import java.security.Principal

/**
 * Represents the controller that contains the endpoints related to the user.
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    /**
     * Gets the session user.
     *
     * @param authentication the authentication token.
     */
    @GetMapping("/session")
    fun getSession(authentication: Principal): ResponseEntity<*> =
        when (val user = userService.getByEmail(authentication.toEmail())) {
            is Success -> responde(GetUserOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    /**
     * Gets a user by its id.
     *
     * @param id the user id.
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val user = userService.getById(id)) {
            is Success -> responde(GetUserOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    /**
     * Gets all users.
     *
     * @return a list of users.
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val users = userService.getAll()) {
            is Success -> responde(getAllUsersOutputModel(users.value))
            is Failure -> Problem.responseForError(users.value)
        }


    /**
     * Saves a user.
     *
     * @param saveUserInputModel the user to save.
     */
    @PostMapping("/save")
    fun save(@RequestBody saveUserInputModel: SaveUserInputModel): ResponseEntity<*> =
        when (val user = userService.save(saveUserInputModel)) {
            is Success -> responde(SaveUserOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    /**
     * Updates a user.
     *
     * @param updateInputModel the user to update.
     */
    @PutMapping("/update")
    fun update(@RequestBody updateInputModel: UpdateUserInputModel): ResponseEntity<*> =
        when (val user = userService.update(updateInputModel)) {
            is Success -> responde(UpdateUserOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    /**
     * Deletes a user by its id.
     *
     * @param id the user id.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val user = userService.delete(id)) {
            is Success -> responde(GetUserOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }
}
