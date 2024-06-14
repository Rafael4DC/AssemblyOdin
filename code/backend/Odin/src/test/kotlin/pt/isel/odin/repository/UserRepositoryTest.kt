package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.utils.TestData

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
        val savedRole = roleRepository.save(TestData.role1)
        val user = TestData.user1.copy(role = savedRole)

        // when: saving the user
        val savedUser = userRepository.save(user)

        // then: validate the save operation
        assertNotNull(savedUser.id)
        assertEquals(TestData.user1.email, savedUser.email)
        assertEquals(TestData.user1.username, savedUser.username)
        assertEquals(TestData.user1.credits, savedUser.credits)
        assertEquals(savedRole.id, savedUser.role.id)
    }

    @Test
    fun `Save User with duplicate email`() {
        // given: a Role instance and two User instances with the same email
        val savedRole = roleRepository.save(TestData.role2)
        val user1 = TestData.user2.copy(role = savedRole)
        userRepository.save(user1)
        val user2 = TestData.user2.copy(role = savedRole)

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
        val savedRole = roleRepository.save(TestData.role3)
        val user = TestData.user4.copy(role = savedRole)
        val savedUser = userRepository.save(user)

        // when: retrieving the user by ID
        val retrievedUser = userRepository.findById(savedUser.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedUser)
        assertEquals(TestData.user4.email, retrievedUser?.email)
        assertEquals(TestData.user4.username, retrievedUser?.username)
        assertEquals(savedRole.id, retrievedUser?.role?.id)
    }

    @Test
    fun `Find User by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the user by non-existent ID
        val retrievedUser = userRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedUser)
    }

    @Test
    fun `Find User by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the user by negative ID
        val retrievedUser = userRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedUser)
    }

    @Test
    fun `Find all Users`() {
        // given: multiple saved User instances
        val savedRole = roleRepository.save(TestData.role4)
        val user1 = TestData.user5.copy(role = savedRole)
        val user2 = TestData.user6.copy(role = savedRole)
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
        val savedRole = roleRepository.save(TestData.role5)
        val user = TestData.user7.copy(role = savedRole)
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
        val savedRole = roleRepository.save(TestData.role6)
        val user = TestData.user8.copy(role = savedRole)
        val savedUser = userRepository.save(user)

        // when: deleting the user
        userRepository.deleteById(savedUser.id!!)

        // then: validate the delete operation
        val deletedUser = userRepository.findById(savedUser.id!!).orElse(null)
        assertNull(deletedUser)
    }
}
