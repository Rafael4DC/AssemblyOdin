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
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.user.models.GetUserLogsOutputModel
import pt.isel.odin.http.controllers.user.models.GetUserOutputModel
import pt.isel.odin.http.controllers.user.models.SaveUserInputModel
import pt.isel.odin.http.controllers.user.models.SaveUserOutputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserInputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserOutputModel
import pt.isel.odin.http.controllers.user.models.getAllRolesOutputModel
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
@RequestMapping(Uris.Users.RESOURCE)
class UserController(private val userService: UserService) {

    /**
     * Gets the session user.
     *
     * @param authentication the authentication token.
     *
     * @return Session user info
     */
    @GetMapping(Uris.Users.SESSION)
    fun getSession(authentication: Principal): ResponseEntity<*> =
        when (val result = userService.getByEmail(authentication.toEmail())) {
            is Success -> responde(GetUserOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets the session user with logs.
     *
     * @param authentication the authentication token.
     *
     * @return Session user info with logs.
     */
    @GetMapping(Uris.Users.GET_LOGS)
    fun getSessionWithLogs(authentication: Principal): ResponseEntity<*> =
        when (val result = userService.getByEmailAndLogs(authentication.toEmail())) {
            is Success -> responde(GetUserLogsOutputModel(result.value.first, result.value.second))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets a user by its id.
     *
     * @param id the user id.
     *
     * @return The user.
     */
    @GetMapping(Uris.Users.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = userService.getById(id)) {
            is Success -> responde(GetUserOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all users.
     *
     * @return a list of users.
     *
     * @return All users.
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = userService.getAll()) {
            is Success -> responde(getAllUsersOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a user.
     *
     * @param saveUserInputModel the user to save.
     *
     * @return The saved user id.
     */
    @PostMapping(Uris.Users.SAVE)
    fun save(@RequestBody saveUserInputModel: SaveUserInputModel): ResponseEntity<*> =
        when (val result = userService.save(saveUserInputModel)) {
            is Success -> responde(
                SaveUserOutputModel(result.value),
                201,
                Uris.Users.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates a user.
     *
     * @param updateInputModel the user to update.
     *
     * @return The updated user.
     */
    @PutMapping(Uris.Users.UPDATE)
    fun update(@RequestBody updateInputModel: UpdateUserInputModel): ResponseEntity<*> =
        when (val result = userService.update(updateInputModel)) {
            is Success -> responde(UpdateUserOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Deletes a user by its id.
     *
     * @param id the user id.
     *
     * @return The deleted user.
     */
    @DeleteMapping(Uris.Users.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = userService.delete(id)) {
            is Success -> responde(GetUserOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all students.
     *
     * @return All students.
     */
    @GetMapping(Uris.Users.GET_STUDENTS)
    fun getStudents(): ResponseEntity<*> =
        when (val result = userService.getStudents()) {
            is Success -> responde(getAllUsersOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Get All Roles.
     *
     * @return All Roles.
     */
    @GetMapping(Uris.Users.GET_ROLES)
    fun getRoles(): ResponseEntity<*> =
        when (val result = userService.getRoles()) {
            is Success -> responde(getAllRolesOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
