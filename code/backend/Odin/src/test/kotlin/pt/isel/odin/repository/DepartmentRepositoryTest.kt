package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Department

@DataJpaTest
@Transactional
class DepartmentRepositoryTest {

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Save Department`() {
        // given: a Department instance
        val department = Department(name = "Engineering")

        // when: saving the department
        val savedDepartment = departmentRepository.save(department)

        // then: validate the save operation
        assertNotNull(savedDepartment.id)
        assertEquals("Engineering", savedDepartment.name)
    }

    @Test
    fun `Find Department by ID correctly`() {
        // given: a saved Department instance
        val department = Department(name = "HR")
        val savedDepartment = departmentRepository.save(department)

        // when: retrieving the department by ID
        val retrievedDepartment = departmentRepository.findById(savedDepartment.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedDepartment)
        assertEquals("HR", retrievedDepartment.name)
    }

    @Test
    fun `Find Department by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the department by non-existent ID
        val retrievedDepartment = departmentRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedDepartment)
    }

    @Test
    fun `Find Department by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the department by negative ID
        val retrievedDepartment = departmentRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedDepartment)
    }

    @Test
    fun `Find all Departments`() {
        // given: multiple saved Department instances
        val department1 = Department(name = "HR")
        val department2 = Department(name = "Finance")
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
        val department = Department(name = "Marketing")
        val savedDepartment = departmentRepository.save(department)

        // when: updating the department's name
        val updatedDepartment = departmentRepository.save(savedDepartment.copy(name = "Sales"))

        // then: validate the update operation
        assertEquals("Sales", updatedDepartment.name)
    }

    @Test
    fun `Delete Department`() {
        // given: a saved Department instance
        val department = Department(name = "Logistics")
        val savedDepartment = departmentRepository.save(department)

        // when: deleting the department
        departmentRepository.deleteById(savedDepartment.id!!)

        // then: validate the delete operation
        val deletedDepartment = departmentRepository.findById(savedDepartment.id!!).orElse(null)
        assertNull(deletedDepartment)
    }
}