package pt.isel.odin.service.interfaces

import pt.isel.odin.controller.dto.user.UserRequest
import pt.isel.odin.model.User

/**
 * Service for users.
 */
interface UserService {

    /**
     * Gets a user by its id.
     *
     * @param id the user id.
     */
    fun getById(id: String): User

    /**
     * Gets all users.
     */
    fun getAll(): List<User>

    /**
     * Saves or updates a user.
     *
     * @param userRequest the user to save.
     */
    fun save(userRequest: UserRequest): User

    /**
     * Deletes a user by its id.
     *
     * @param id the user id.
     */
    fun delete(id: String)
}
