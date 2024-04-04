package pt.isel.odin.service.interfaces

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
    fun getById(id: Long): User

    /**
     * Gets all users.
     */
    fun getAll(): List<User>

    /**
     * Saves or updates a user.
     *
     * @param user the user to save.
     */
    fun save(user: User): User

    /**
     * Deletes a user by its id.
     *
     * @param id the user id.
     */
    fun delete(id: Long)
}
