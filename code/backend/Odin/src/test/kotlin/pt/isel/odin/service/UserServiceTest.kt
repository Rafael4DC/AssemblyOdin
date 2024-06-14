package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import pt.isel.odin.http.controllers.user.models.SaveUserInputModel
import pt.isel.odin.http.controllers.user.models.UpdateUserInputModel
import pt.isel.odin.model.Role
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.user.UserService
import pt.isel.odin.service.user.error.DeleteUserError
import pt.isel.odin.service.user.error.GetUserError
import pt.isel.odin.service.user.error.SaveUpdateUserError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Test
    fun `Get user by ID`() {
        // given: a saved User instance
        val role = Role(name = "Admin")
        roleRepository.save(role)
        val user = User(email = "admin@example.com", username = "admin", role = role)
        userRepository.save(user)

        // when: retrieving the user by ID
        val result = userService.getById(user.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(user, (result as Success).value)
    }

    @Test
    fun `Get user by non-existent ID`() {
        // when: retrieving the user by non-existent ID
        val result = userService.getById(1)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetUserError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Get user by email`() {
        // given: a saved User instance
        val role = Role(name = "Admin")
        roleRepository.save(role)
        val user = User(email = "admin@example.com", username = "admin", role = role)
        userRepository.save(user)

        // when: retrieving the user by email
        val result = userService.getByEmail("admin@example.com")

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(user, (result as Success).value)
    }

    @Test
    fun `Get user by non-existent email`() {
        // when: retrieving the user by non-existent email
        val result = userService.getByEmail("nonexistent@example.com")

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetUserError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Get all users`() {
        // given: multiple saved User instances
        val role = Role(name = "Admin")
        roleRepository.save(role)
        val user1 = User(email = "admin1@example.com", username = "admin1", role = role)
        val user2 = User(email = "admin2@example.com", username = "admin2", role = role)
        userRepository.save(user1)
        userRepository.save(user2)

        // when: retrieving all users
        val result = userService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(user1, user2), (result as Success).value)
    }

    @Test
    fun `Save user`() {
        // given: a valid SaveUserInputModel
        val role = Role(name = "User")
        val roleId = roleRepository.save(role).id!!
        val saveUserInputModel = SaveUserInputModel(email = "user@example.com", username = "user", role = roleId)

        // when: saving the user
        val result = userService.save(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Success)
        val user = User(
            id = (result as Success).value.id!!,
            email = saveUserInputModel.email,
            username = saveUserInputModel.username,
            role = role,
            credits = saveUserInputModel.credits
        )
        assertEquals(user, result.value)
    }

    @Test
    fun `Save user with invalid name`() {
        // given: a valid SaveUserInputModel
        val saveUserInputModel = SaveUserInputModel(email = "user@example.com", username = "", role = 1)

        // when: saving the user
        val result = userService.save(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.IncorrectNameUser, (result as Failure).value)
    }

    @Test
    fun `Save user with invalid email`() {
        // given: a valid SaveUserInputModel
        val saveUserInputModel = SaveUserInputModel(email = "", username = "user", role = 1)

        // when: saving the user
        val result = userService.save(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.IncorrectEmailUser, (result as Failure).value)
    }

    @Test
    fun `Save user with duplicate email`() {
        // given: a SaveUserInputModel with an existing email
        val role = Role(name = "User")
        roleRepository.save(role)
        val saveUserInputModel = SaveUserInputModel(email = "user@example.com", username = "user", role = 1)
        val existingUser = User(email = saveUserInputModel.email, username = saveUserInputModel.username, role = role)
        userRepository.save(existingUser)

        // when: saving the user with duplicate email
        val result = userService.save(saveUserInputModel)

        // then: validate the failure due to duplicate email
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.EmailAlreadyExistsUser, (result as Failure).value)
    }

    @Test
    fun `Save user with invalid role`() {
        // given: a valid SaveUserInputModel
        val saveUserInputModel = SaveUserInputModel(email = "user@example.com", username = "user", role = 9999)

        // when: saving the user
        val result = userService.save(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.RoleIncorrectUser, (result as Failure).value)
    }

    @Test
    fun `Update user`() {
        // given: a valid UpdateUserInputModel and existing user
        val role = Role(name = "User")
        val roleId = roleRepository.save(role).id!!
        val existingUser = User(email = "user@example.com", username = "user", role = role)
        val user = userRepository.save(existingUser)
        val updateUserInputModel =
            UpdateUserInputModel(
                id = user.id!!,
                email = "userupdated@example.com",
                username = "userupdated",
                role = roleId,
                credits = 100
            )
        val updatedUser =
            existingUser.copy(
                id = user.id!!,
                email = updateUserInputModel.email,
                username = updateUserInputModel.username,
                credits = updateUserInputModel.credits
            )

        // when: updating the user
        val result = userService.update(updateUserInputModel)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedUser, (result as Success).value)
    }

    @Test
    fun `Update non-existent user`() {
        // given: an UpdateUserInputModel for a non-existent user
        val role = Role(name = "User")
        val roleId = roleRepository.save(role).id!!
        val updateUserInputModel = UpdateUserInputModel(
            id = 1,
            email = "userupdated@example.com",
            username = "userupdated",
            role = roleId,
            credits = 100
        )

        // when: updating the non-existent user
        val result = userService.update(updateUserInputModel)

        // then: validate the failure due to non-existent user
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Update user with invalid name`() {
        // given: a valid SaveUserInputModel
        val saveUserInputModel = UpdateUserInputModel(id = 1, email = "user@example.com", username = "", role = 1)

        // when: saving the user
        val result = userService.update(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.IncorrectNameUser, (result as Failure).value)
    }

    @Test
    fun `Update user with invalid email`() {
        // given: a valid SaveUserInputModel
        val saveUserInputModel = UpdateUserInputModel(id = 1, email = "", username = "user", role = 1)

        // when: saving the user
        val result = userService.update(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.IncorrectEmailUser, (result as Failure).value)
    }

    @Test
    fun `Update user with invalid role`() {
        // given: a valid SaveUserInputModel
        val saveUserInputModel =
            UpdateUserInputModel(id = 1, email = "user@example.com", username = "user", role = 9999)

        // when: saving the user
        val result = userService.update(saveUserInputModel)

        // then: validate the save operation
        assertTrue(result is Failure)
        assertEquals(SaveUpdateUserError.RoleIncorrectUser, (result as Failure).value)
    }

    @Test
    fun `Delete user`() {
        // given: an existing user ID
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "user@example.com", username = "user", role = role)
        userRepository.save(user)

        // when: deleting the user
        val result = userService.delete(user.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(user, (result as Success).value)

        // and: verify the delete operation
        val deletedUser = userRepository.findById(user.id!!)
        assertTrue(deletedUser.isEmpty)
    }

    @Test
    fun `Delete non-existent user`() {
        // when: deleting the non-existent user
        val result = userService.delete(1)

        // then: validate the failure due to non-existent user
        assertTrue(result is Failure)
        assertEquals(DeleteUserError.NotFoundUser, (result as Failure).value)
    }
}
