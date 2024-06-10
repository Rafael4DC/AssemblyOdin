package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Role
import pt.isel.odin.model.user.User

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Test
    fun `Save User`() {
        // given: a Role instance and a User instance
        val role = Role(name = "Admin")
        val savedRole = roleRepository.save(role)
        val user = User(email = "admin@example.com", username = "admin", role = savedRole, credits = 100)

        // when: saving the user
        val savedUser = userRepository.save(user)

        // then: validate the save operation
        assertNotNull(savedUser.id)
        assertEquals("admin@example.com", savedUser.email)
        assertEquals("admin", savedUser.username)
        assertEquals(100, savedUser.credits)
        assertEquals(savedRole.id, savedUser.role.id)
    }

    @Test
    fun `Save User with duplicate email`() {
        // given: a Role instance and two User instances with the same email
        val role = Role(name = "User")
        val savedRole = roleRepository.save(role)
        val user1 = User(email = "duplicate@example.com", username = "user1", role = savedRole)
        userRepository.save(user1)
        val user2 = User(email = "duplicate@example.com", username = "user2", role = savedRole)

        // when: saving the second user with duplicate email
        val exception = assertThrows<DataIntegrityViolationException> {
            userRepository.save(user2)
        }

        // then: validate the exception
        assertNotNull(exception)
    }

    @Test
    fun `Find User by ID`() {
        // given: a saved Role instance and a saved User instance
        val role = Role(name = "Manager")
        val savedRole = roleRepository.save(role)
        val user = User(email = "manager@example.com", username = "manager", role = savedRole)
        val savedUser = userRepository.save(user)

        // when: retrieving the user by ID
        val retrievedUser = userRepository.findById(savedUser.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedUser)
        assertEquals("manager@example.com", retrievedUser?.email)
        assertEquals("manager", retrievedUser?.username)
        assertEquals(savedRole.id, retrievedUser?.role?.id)
    }

    @Test
    fun `Find User by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the user by non-existent ID
        val retrievedUser = userRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedUser)
    }

    @Test
    fun `Find User by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the user by negative ID
        val retrievedUser = userRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedUser)
    }

    @Test
    fun `Find all Users`() {
        // given: multiple saved User instances
        val role = Role(name = "Employee")
        val savedRole = roleRepository.save(role)
        val user1 = User(email = "employee1@example.com", username = "employee1", role = savedRole)
        val user2 = User(email = "employee2@example.com", username = "employee2", role = savedRole)
        userRepository.save(user1)
        userRepository.save(user2)

        // when: retrieving all users
        val users = userRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, users.size)
    }

    @Test
    fun `Update User`() {
        // given: a saved Role instance and a saved User instance
        val role = Role(name = "Support")
        val savedRole = roleRepository.save(role)
        val user = User(email = "support@example.com", username = "support", role = savedRole)
        val savedUser = userRepository.save(user)

        // when: updating the user's username and credits
        val updatedUser = userRepository.save(savedUser.copy(username = "supportUpdated", credits = 200))

        // then: validate the update operation
        assertEquals("supportUpdated", updatedUser.username)
        assertEquals(200, updatedUser.credits)
    }

    @Test
    fun `Delete User`() {
        // given: a saved Role instance and a saved User instance
        val role = Role(name = "Operations")
        val savedRole = roleRepository.save(role)
        val user = User(email = "operations@example.com", username = "operations", role = savedRole)
        val savedUser = userRepository.save(user)

        // when: deleting the user
        userRepository.deleteById(savedUser.id!!)

        // then: validate the delete operation
        val deletedUser = userRepository.findById(savedUser.id!!).orElse(null)
        assertNull(deletedUser)
    }
}
