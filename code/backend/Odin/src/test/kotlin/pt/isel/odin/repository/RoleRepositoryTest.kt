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

@DataJpaTest
@Transactional
class RoleRepositoryTest {

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Test
    fun `Save Role`() {
        // given: a Role instance
        val role = Role(name = "Admin")

        // when: saving the role
        val savedRole = roleRepository.save(role)

        // then: validate the save operation
        assertNotNull(savedRole.id)
        assertEquals("Admin", savedRole.name)
    }

    @Test
    fun `Save Role with null name`() {
        // given: a Role instance with a null name
        val role = Role(name = null)

        // when: saving the role with null name
        val exception = assertThrows<DataIntegrityViolationException> {
            roleRepository.save(role)
        }

        // then: validate the exception
        assertNotNull(exception)
    }

    @Test
    fun `Save Role with duplicate name`() {
        // given: a Role instance with a duplicate name
        val role1 = Role(name = "User")
        roleRepository.save(role1)
        val role2 = Role(name = "User")

        // when: saving the role with duplicate name
        val exception = assertThrows<DataIntegrityViolationException> {
            roleRepository.save(role2)
        }

        // then: validate the exception
        assertNotNull(exception)
    }

    @Test
    fun `Find Role by ID`() {
        // given: a saved Role instance
        val role = Role(name = "Manager")
        val savedRole = roleRepository.save(role)

        // when: retrieving the role by ID
        val retrievedRole = roleRepository.findById(savedRole.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedRole)
        assertEquals("Manager", retrievedRole?.name)
    }

    @Test
    fun `Find Role by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the role by non-existent ID
        val retrievedRole = roleRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedRole)
    }

    @Test
    fun `Find Role by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the role by negative ID
        val retrievedRole = roleRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedRole)
    }

    @Test
    fun `Find all Roles`() {
        // given: multiple saved Role instances
        val role1 = Role(name = "Developer")
        val role2 = Role(name = "Tester")
        roleRepository.save(role1)
        roleRepository.save(role2)

        // when: retrieving all roles
        val roles = roleRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, roles.size)
    }

    @Test
    fun `Update Role`() {
        // given: a saved Role instance
        val role = Role(name = "Support")
        val savedRole = roleRepository.save(role)

        // when: updating the role's name
        val updatedRole = roleRepository.save(savedRole.copy(name = "Customer Support"))

        // then: validate the update operation
        assertEquals("Customer Support", updatedRole.name)
    }

    @Test
    fun `Delete Role`() {
        // given: a saved Role instance
        val role = Role(name = "Operations")
        val savedRole = roleRepository.save(role)

        // when: deleting the role
        roleRepository.deleteById(savedRole.id!!)

        // then: validate the delete operation
        val deletedRole = roleRepository.findById(savedRole.id!!).orElse(null)
        assertNull(deletedRole)
    }
}

