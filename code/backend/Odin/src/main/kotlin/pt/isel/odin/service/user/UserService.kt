package pt.isel.odin.service.user

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.user.models.SaveUserInputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserInputModel
import pt.isel.odin.model.user.User
import pt.isel.odin.model.Role
import pt.isel.odin.model.user.UserDomain
import pt.isel.odin.repository.CreditLogRepository
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.user.error.DeleteUserError
import pt.isel.odin.service.user.error.GetUserError
import pt.isel.odin.service.user.error.SaveUpdateUserError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

/**
 * Service for Users.
 */
@Service
class UserService(
    private val userDomain: UserDomain,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val creditLogRepository: CreditLogRepository
) {

    /**
     * Gets a user by its id.
     *
     * @param id the user id
     *
     * @return the [GetUserResult] if found, [GetUserError.NotFoundUser] otherwise
     */
    fun getById(id: Long): GetUserResult {
        return userRepository.findById(id)
            .map<GetUserResult> { user -> success(user) }
            .orElse(failure(GetUserError.NotFoundUser))
    }

    /**
     * Gets a user by its email.
     *
     * @param email the user email
     *
     * @return the [GetUserResult] if found, [GetUserError.NotFoundUser] otherwise
     */
    fun getByEmail(email: String): GetUserResult =
        userRepository.findByEmail(email)
            .map<GetUserResult> { user -> success(user) }
            .orElse(failure(GetUserError.NotFoundUser))

    /**
     * Gets a user by its email and logs.
     *
     * @param email the user email
     *
     * @return the [GetUserResult] if found, [GetUserError.NotFoundUser] otherwise
     */
    fun getByEmailAndLogs(email: String): GetUserWithLogsResult {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) return failure(GetUserError.NotFoundUser)
        val user = userOptional.get()
        val logs = creditLogRepository.findByUserId(user.id!!)
        return success(Pair(user, logs.orElse(emptyList())))
    }

    /**
     * Gets all users.
     *
     * @return the [GetAllUsersResult] with the list of [User]
     */
    fun getAll(): GetAllUsersResult = success(userRepository.findAll())

    /**
     * Saves a user.
     *
     * @param saveInputModel the user to save
     *
     * @return the [CreationUserResult] if saved, [SaveUpdateUserError] otherwise
     */
    @Transactional
    fun save(saveInputModel: SaveUserInputModel): CreationUserResult {
        validateUserInput(saveInputModel.username, saveInputModel.email)?.let { return it }

        if (userRepository.findByEmail(saveInputModel.email).isPresent) {
            return failure(SaveUpdateUserError.EmailAlreadyExistsUser)
        }

        val role = roleRepository.findById(saveInputModel.role)
        if (role.isEmpty) return failure(SaveUpdateUserError.RoleIncorrectUser)

        return success(userRepository.save(saveInputModel.toUser(role.get())))
    }

    /**
     * Updates a user.
     *
     * @param updateInputModel the user to update
     *
     * @return the [CreationUserResult] if updated, [SaveUpdateUserError] otherwise
     */
    @Transactional
    fun update(updateInputModel: UpdateUserInputModel): CreationUserResult {
        validateUserInput(updateInputModel.username, updateInputModel.email)?.let { return it }

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
     * Deletes a user.
     *
     * @param id the user id
     *
     * @return the [DeleteUserResult] if deleted, [DeleteUserError] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteUserResult =
        userRepository.findById(id)
            .map<DeleteUserResult> { user ->
                userRepository.delete(user)
                success(user)
            }.orElse(failure(DeleteUserError.NotFoundUser))

    /**
     * Gets all students.
     *
     * @return the [GetAllUsersResult] with the list of [User]
     */
    fun getStudents(): GetAllUsersResult {
        return success(userRepository.findUserByRole_NameIs("STUDENT"))
        /*.map<GetAllUsersResult> { users -> success(users) }
        .orElse(failure(GetUserError.NotFoundUser))*/
    }

    /**
     * Gets all roles.
     *
     * @return the [GetAllRolesResult] with the list of [Role]
     */
    fun getRoles(): GetAllRolesResult = success(roleRepository.findAll())

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
