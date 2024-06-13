package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.utils.TestData

@DataJpaTest
@Transactional
class DepartmentRepositoryTest {

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Save Department`() {
        // given: a Department instance
        val department = TestData.department1

        // when: saving the department
        val savedDepartment = departmentRepository.save(department)

        // then: validate the save operation
        assertNotNull(savedDepartment.id)
        assertEquals(TestData.department1.name, savedDepartment.name)
    }

    @Test
    fun `Find Department by ID correctly`() {
        // given: a saved Department instance
        val department = TestData.department2
        val savedDepartment = departmentRepository.save(department)

        // when: retrieving the department by ID
        val retrievedDepartment = departmentRepository.findById(savedDepartment.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedDepartment)
        assertEquals(TestData.department2.name, retrievedDepartment.name)
    }

    @Test
    fun `Find Department by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the department by non-existent ID
        val retrievedDepartment = departmentRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedDepartment)
    }

    @Test
    fun `Find Department by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the department by negative ID
        val retrievedDepartment = departmentRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedDepartment)
    }

    @Test
    fun `Find all Departments`() {
        // given: multiple saved Department instances
        val department1 = TestData.department2
        val department2 = TestData.department3
        departmentRepository.save(department1)
        departmentRepository.save(department2)

        // when: retrieving all departments
        val departments = departmentRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, departments.size)
    }

    @Test
    fun `Update Department`() {
        // given: a saved Department instance
        val department = TestData.department4
        val savedDepartment = departmentRepository.save(department)

        // when: updating the department's name
        val updatedDepartment = departmentRepository.save(savedDepartment.copy(name = TestData.department5.name))

        // then: validate the update operation
        assertEquals(TestData.department5.name, updatedDepartment.name)
    }

    @Test
    fun `Delete Department`() {
        // given: a saved Department instance
        val department = TestData.department5
        val savedDepartment = departmentRepository.save(department)

        // when: deleting the department
        departmentRepository.deleteById(savedDepartment.id!!)

        // then: validate the delete operation
        val deletedDepartment = departmentRepository.findById(savedDepartment.id!!).orElse(null)
        assertNull(deletedDepartment)
    }
}
