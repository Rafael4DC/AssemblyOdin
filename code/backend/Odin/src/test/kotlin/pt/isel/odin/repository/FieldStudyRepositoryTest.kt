package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.utils.TestData

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
        val savedDepartment = departmentRepository.save(TestData.department1)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy1.name, department = savedDepartment)

        // when: saving the fieldStudy
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // then: validate the save operation
        assertNotNull(savedFieldStudy.id)
        assertEquals(TestData.fieldStudy1.name, savedFieldStudy.name)
        assertEquals(savedDepartment.id, savedFieldStudy.department.id)
    }

    @Test
    fun `Find FieldStudy by ID`() {
        // given: a saved Department instance and a saved FieldStudy instance
        val savedDepartment = departmentRepository.save(TestData.department2)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy2.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // when: retrieving the fieldStudy by ID
        val retrievedFieldStudy = fieldStudyRepository.findById(savedFieldStudy.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedFieldStudy)
        assertEquals(TestData.fieldStudy2.name, retrievedFieldStudy?.name)
        assertEquals(savedDepartment.id, retrievedFieldStudy?.department?.id)
    }

    @Test
    fun `Find FieldStudy by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the fieldStudy by non-existent ID
        val retrievedFieldStudy = fieldStudyRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedFieldStudy)
    }

    @Test
    fun `Find FieldStudy by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the fieldStudy by negative ID
        val retrievedFieldStudy = fieldStudyRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedFieldStudy)
    }

    @Test
    fun `Find all FieldStudies`() {
        // given: multiple saved FieldStudy instances
        val savedDepartment = departmentRepository.save(TestData.department3)
        val fieldStudy1 = FieldStudy(name = TestData.fieldStudy3.name, department = savedDepartment)
        val fieldStudy2 = FieldStudy(name = TestData.fieldStudy4.name, department = savedDepartment)
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
        val savedDepartment = departmentRepository.save(TestData.department4)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy5.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // when: updating the fieldStudy's name
        val updatedFieldStudy = fieldStudyRepository.save(savedFieldStudy.copy(name = "Ethics"))

        // then: validate the update operation
        assertEquals("Ethics", updatedFieldStudy.name)
    }

    @Test
    fun `Delete FieldStudy`() {
        // given: a saved Department instance and a saved FieldStudy instance
        val savedDepartment = departmentRepository.save(TestData.department5)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy5.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)

        // when: deleting the fieldStudy
        fieldStudyRepository.deleteById(savedFieldStudy.id!!)

        // then: validate the delete operation
        val deletedFieldStudy = fieldStudyRepository.findById(savedFieldStudy.id!!).orElse(null)
        assertNull(deletedFieldStudy)
    }
}
