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
import pt.isel.odin.utils.TestData

@DataJpaTest
@Transactional
class RoleRepositoryTest {

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Test
    fun `Save Role`() {
        // given: a Role instance
        val role = TestData.role1

        // when: saving the role
        val savedRole = roleRepository.save(role)

        // then: validate the save operation
        assertNotNull(savedRole.id)
        assertEquals(TestData.role1.name, savedRole.name)
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
        val role1 = TestData.role2
        roleRepository.save(role1)
        val role2 = Role(name = TestData.role2.name)

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
        val role = TestData.role3
        val savedRole = roleRepository.save(role)

        // when: retrieving the role by ID
        val retrievedRole = roleRepository.findById(savedRole.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedRole)
        assertEquals(TestData.role3.name, retrievedRole?.name)
    }

    @Test
    fun `Find Role by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the role by non-existent ID
        val retrievedRole = roleRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedRole)
    }

    @Test
    fun `Find Role by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the role by negative ID
        val retrievedRole = roleRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedRole)
    }

    @Test
    fun `Find all Roles`() {
        // given: multiple saved Role instances
        val role1 = TestData.role4
        val role2 = TestData.role5
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
        val role = TestData.role6
        val savedRole = roleRepository.save(role)

        // when: updating the role's name
        val updatedRole = roleRepository.save(savedRole.copy(name = "Customer Support"))

        // then: validate the update operation
        assertEquals("Customer Support", updatedRole.name)
    }

    @Test
    fun `Delete Role`() {
        // given: a saved Role instance
        val role = TestData.role7
        val savedRole = roleRepository.save(role)

        // when: deleting the role
        roleRepository.deleteById(savedRole.id!!)

        // then: validate the delete operation
        val deletedRole = roleRepository.findById(savedRole.id!!).orElse(null)
        assertNull(deletedRole)
    }
}
