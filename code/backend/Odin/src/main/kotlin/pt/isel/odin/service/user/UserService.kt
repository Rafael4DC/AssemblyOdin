package pt.isel.odin.service.user

import jakarta.transaction.Transactional
import org.hibernate.internal.CoreLogging.logger
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.user.models.SaveUserInputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserInputModel
import pt.isel.odin.model.user.UserDomain
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.user.error.DeleteUserError
import pt.isel.odin.service.user.error.GetUserError
import pt.isel.odin.service.user.error.SaveUpdateUserError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

/**
 * Service that handles the user operations.
 */
@Service
class UserService(
    private val userDomain: UserDomain,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    /**
     * Gets a user by its id.
     *
     * @param id the user id.
     *
     * @return a [GetUserResult] with the user or an error.
     */
    fun getById(id: Long): GetUserResult {
        return userRepository.findById(id)
            .map<GetUserResult> { user -> success(user) }
            .orElse(failure(GetUserError.NotFoundUser))
    }

    /**
     * Gets a user by its email.
     *
     * @param email the user email.
     *
     * @return a [GetUserResult] with the user or an error.
     */
    fun getByEmail(email: String): GetUserResult =
        userRepository.findByEmail(email)
            .map<GetUserResult> { user -> success(user) }
            .orElse(failure(GetUserError.NotFoundUser))

    /**
     * Gets all users.
     *
     * @return a [GetAllUsersResult] with the list of users.
     */
    fun getAll(): GetAllUsersResult = success(userRepository.findAll())

    /**
     * Saves a user.
     *
     * @param saveInputModel the user input model.
     *
     * @return a [CreationUserResult] with the result of the User saved or an error.
     */
    @Transactional
    fun save(saveInputModel: SaveUserInputModel): CreationUserResult {
        validateUserInput(saveInputModel.username, saveInputModel.email)?.let { return it }
        log.info("Validations passed successfully with email: ${saveInputModel.email} and username: ${saveInputModel.username}.")

        if (userRepository.findByEmail(saveInputModel.email).isPresent)
            return failure(SaveUpdateUserError.EmailAlreadyExistsUser)

        val role = roleRepository.findById(saveInputModel.role)
        if (role.isEmpty) return failure(SaveUpdateUserError.RoleIncorrectUser)

        return success(userRepository.save(saveInputModel.toUser(role.get())))
    }

    /**
     * Updates a user.
     *
     * @param updateInputModel the user to update.
     *
     * @return a [CreationUserResult] with the result of the User updated or an error.
     */
    @Transactional
    fun update(updateInputModel: UpdateUserInputModel): CreationUserResult {
        validateUserInput(updateInputModel.username, updateInputModel.email)?.let { return it }
        log.info("Validations passed successfully with email: ${updateInputModel.email} and username: ${updateInputModel.username}.")

        val role = roleRepository.findById(updateInputModel.role)
        if (role.isEmpty) return failure(SaveUpdateUserError.RoleIncorrectUser)

        return userRepository.findById(updateInputModel.id)
            .map<CreationUserResult> { user ->
                success(
                    userRepository.save(
                        user.copy(
                            email = updateInputModel.email,
                            username = updateInputModel.username,
                            credits = updateInputModel.credits,
                            role = role.get()
                        )
                    )
                )
            }.orElse(failure(SaveUpdateUserError.NotFoundUser))
    }

    /**
     * Deletes a user by its id.
     *
     * @param id the user id.
     *
     * @return a [DeleteUserResult] with the result of the User deleted or an error.
     */
    @Transactional
    fun delete(id: Long): DeleteUserResult =
        userRepository.findById(id)
            .map<DeleteUserResult> { user ->
                userRepository.delete(user)
                success(user)
            }.orElse(failure(DeleteUserError.NotFoundUser))

    companion object {
        private val log = logger(UserService::class.java)
    }

    /**
     * Validates the user input.
     *
     * @param username the username to validate.
     * @param email the email to validate.
     *
     * @return null if there are no errors, otherwise a [CreationUserResult] with the error.
     */
    private fun validateUserInput(username: String, email: String): CreationUserResult? {
        if (!userDomain.isUsernameValid(username)) return failure(SaveUpdateUserError.IncorrectNameUser)
        if (!userDomain.isEmailValid(email)) return failure(SaveUpdateUserError.IncorrectEmailUser)
        return null
    }
}
