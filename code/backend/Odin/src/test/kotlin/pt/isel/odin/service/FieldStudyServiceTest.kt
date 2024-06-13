package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyInputModel
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.service.fieldstudy.FieldStudyService
import pt.isel.odin.service.fieldstudy.error.DeleteFieldStudyError
import pt.isel.odin.service.fieldstudy.error.GetFieldStudyError
import pt.isel.odin.service.fieldstudy.error.SaveUpdateFieldStudyError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class FieldStudyServiceTest {

    @Autowired
    private lateinit var fieldStudyService: FieldStudyService

    @Autowired
    private lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Get field study by ID`() {
        // given: a saved FieldStudy instance
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)

        // when: retrieving the field study by ID
        val result = fieldStudyService.getById(fieldStudy.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(fieldStudy, (result as Success).value)
    }

    @Test
    fun `Get field study by non-existent ID`() {
        // when: retrieving the field study by non-existent ID
        val result = fieldStudyService.getById(1)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetFieldStudyError.NotFoundFieldStudy, (result as Failure).value)
    }

    @Test
    fun `Get all fields of study`() {
        // given: multiple saved FieldStudy instances
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy1 = FieldStudy(name = "Physics", department = department)
        val fieldStudy2 = FieldStudy(name = "Chemistry", department = department)
        fieldStudyRepository.save(fieldStudy1)
        fieldStudyRepository.save(fieldStudy2)

        // when: retrieving all fields of study
        val result = fieldStudyService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(fieldStudy1, fieldStudy2), (result as Success).value)
    }

    @Test
    fun `Save field study`() {
        // given: a valid SaveFieldStudyInputModel
        val department = Department(name = "Science")
        val departmentId = departmentRepository.save(department).id!!
        val saveFieldStudyInputModel = SaveFieldStudyInputModel(name = "Physics", department = departmentId)

        // when: saving the field study
        val result = fieldStudyService.save(saveFieldStudyInputModel)

        // then: validate the save operation
        assertTrue(result is Success)
        val fieldStudy = FieldStudy(
            id = (result as Success).value.id!!,
            name = saveFieldStudyInputModel.name,
            department = department
        )
        assertEquals(fieldStudy, result.value)
    }

    @Test
    fun `Save field study with duplicate name`() {
        // given: a SaveFieldStudyInputModel with an existing name
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val saveFieldStudyInputModel = SaveFieldStudyInputModel(name = "Physics", department = department.id!!)
        val existingFieldStudy = FieldStudy(name = saveFieldStudyInputModel.name, department = department)
        fieldStudyRepository.save(existingFieldStudy)

        // when: saving the field study with duplicate name
        val result = fieldStudyService.save(saveFieldStudyInputModel)

        // then: validate the failure due to duplicate name
        assertTrue(result is Failure)
        assertEquals(SaveUpdateFieldStudyError.AlreadyExistsFieldStudy, (result as Failure).value)
    }

    @Test
    fun `Save field study with non-existent department`() {
        // given: a SaveFieldStudyInputModel with a non-existent department
        val saveFieldStudyInputModel = SaveFieldStudyInputModel(name = "Physics", department = 9999)

        // when: saving the field study
        val result = fieldStudyService.save(saveFieldStudyInputModel)

        // then: validate the failure due to non-existent department
        assertTrue(result is Failure)
        assertEquals(SaveUpdateFieldStudyError.NotFoundDepartment, (result as Failure).value)
    }

    @Test
    fun `Save field study with duplicate nam1`() {
        // given: a SaveFieldStudyInputModel with an existing name
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val saveFieldStudyInputModel = SaveFieldStudyInputModel(name = "Physics", department = department.id!!)
        val existingFieldStudy = FieldStudy(name = saveFieldStudyInputModel.name, department = department)
        fieldStudyRepository.save(existingFieldStudy)

        // when: saving the field study with duplicate name
        val result = fieldStudyService.save(saveFieldStudyInputModel)

        // then: validate the failure due to duplicate name
        assertTrue(result is Failure)
        assertEquals(SaveUpdateFieldStudyError.AlreadyExistsFieldStudy, (result as Failure).value)
    }


    @Test
    fun `Update field study`() {
        // given: a valid UpdateFieldStudyInputModel and existing field study
        val department = Department(name = "Science")
        val departmentId = departmentRepository.save(department).id!!
        val existingFieldStudy = FieldStudy(name = "Physics", department = department)
        val fieldStudy = fieldStudyRepository.save(existingFieldStudy)
        val updateFieldStudyInputModel =
            UpdateFieldStudyInputModel(
                id = fieldStudy.id!!,
                name = "Astrophysics",
                department = departmentId
            )
        val updatedFieldStudy =
            existingFieldStudy.copy(
                id = fieldStudy.id!!,
                name = updateFieldStudyInputModel.name
            )

        // when: updating the field study
        val result = fieldStudyService.update(updateFieldStudyInputModel)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedFieldStudy, (result as Success).value)
    }

    @Test
    fun `Update non-existent field study`() {
        // given: an UpdateFieldStudyInputModel for a non-existent field study
        val department = Department(name = "Science")
        val departmentId = departmentRepository.save(department).id!!
        val updateFieldStudyInputModel =
            UpdateFieldStudyInputModel(id = 1, name = "Astrophysics", department = departmentId)

        // when: updating the non-existent field study
        val result = fieldStudyService.update(updateFieldStudyInputModel)

        // then: validate the failure due to non-existent field study
        assertTrue(result is Failure)
        assertEquals(SaveUpdateFieldStudyError.NotFoundFieldStudy, (result as Failure).value)
    }

    @Test
    fun `Update field study with non-existent department`() {
        // given: a valid UpdateFieldStudyInputModel with non-existent department
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val existingFieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(existingFieldStudy)
        val updateFieldStudyInputModel =
            UpdateFieldStudyInputModel(id = existingFieldStudy.id!!, name = "Astrophysics", department = 9999)

        // when: updating the field study
        val result = fieldStudyService.update(updateFieldStudyInputModel)

        // then: validate the failure due to non-existent department
        assertTrue(result is Failure)
        assertEquals(SaveUpdateFieldStudyError.NotFoundDepartment, (result as Failure).value)
    }

    @Test
    fun `Delete field study`() {
        // given: an existing field study ID
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)

        // when: deleting the field study
        val result = fieldStudyService.delete(fieldStudy.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(fieldStudy, (result as Success).value)

        // and: verify the delete operation
        val deletedFieldStudy = fieldStudyRepository.findById(fieldStudy.id!!)
        assertTrue(deletedFieldStudy.isEmpty)
    }

    @Test
    fun `Delete non-existent field study`() {
        // when: deleting the non-existent field study
        val result = fieldStudyService.delete(1)

        // then: validate the failure due to non-existent field study
        assertTrue(result is Failure)
        assertEquals(DeleteFieldStudyError.NotFoundFieldStudy, (result as Failure).value)
    }
}
