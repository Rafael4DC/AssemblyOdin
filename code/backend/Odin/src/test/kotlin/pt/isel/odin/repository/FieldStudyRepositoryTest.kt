package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy

@DataJpaTest
@Transactional
class FieldStudyRepositoryTest {

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Save FieldStudy`() {
        // given: a Department instance and a FieldStudy instance
        val department = Department(name = "Science")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = savedDepartment)

        // when: saving the fieldStudy
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // then: validate the save operation
        assertNotNull(savedFieldStudy.id)
        assertEquals("Physics", savedFieldStudy.name)
        assertEquals(savedDepartment.id, savedFieldStudy.department.id)
    }

    @Test
    fun `Find FieldStudy by ID`() {
        // given: a saved Department instance and a saved FieldStudy instance
        val department = Department(name = "Arts")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "History", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // when: retrieving the fieldStudy by ID
        val retrievedFieldStudy = fieldStudyRepository.findById(savedFieldStudy.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedFieldStudy)
        assertEquals("History", retrievedFieldStudy?.name)
        assertEquals(savedDepartment.id, retrievedFieldStudy?.department?.id)
    }

    @Test
    fun `Find FieldStudy by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the fieldStudy by non-existent ID
        val retrievedFieldStudy = fieldStudyRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedFieldStudy)
    }

    @Test
    fun `Find FieldStudy by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the fieldStudy by negative ID
        val retrievedFieldStudy = fieldStudyRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedFieldStudy)
    }

    @Test
    fun `Find all FieldStudies`() {
        // given: multiple saved FieldStudy instances
        val department = Department(name = "Social Sciences")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy1 = FieldStudy(name = "Sociology", department = savedDepartment)
        val fieldStudy2 = FieldStudy(name = "Anthropology", department = savedDepartment)
        fieldStudyRepository.save(fieldStudy1)
        fieldStudyRepository.save(fieldStudy2)

        // when: retrieving all fieldStudies
        val fieldStudies = fieldStudyRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, fieldStudies.size)
    }

    @Test
    fun `Update FieldStudy`() {
        // given: a saved Department instance and a saved FieldStudy instance
        val department = Department(name = "Humanities")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Philosophy", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // when: updating the fieldStudy's name
        val updatedFieldStudy = fieldStudyRepository.save(savedFieldStudy.copy(name = "Ethics"))

        // then: validate the update operation
        assertEquals("Ethics", updatedFieldStudy.name)
    }

    @Test
    fun `Delete FieldStudy`() {
        // given: a saved Department instance and a saved FieldStudy instance
        val department = Department(name = "Law")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Criminal Law", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // when: deleting the fieldStudy
        fieldStudyRepository.deleteById(savedFieldStudy.id!!)

        // then: validate the delete operation
        val deletedFieldStudy = fieldStudyRepository.findById(savedFieldStudy.id!!).orElse(null)
        assertNull(deletedFieldStudy)
    }
}

