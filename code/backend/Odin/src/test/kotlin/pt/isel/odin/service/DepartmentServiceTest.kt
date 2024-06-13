package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pt.isel.odin.http.controllers.department.models.SaveDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentInputModel
import pt.isel.odin.model.Department
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.service.department.DepartmentService
import pt.isel.odin.service.department.error.DeleteDepartmentError
import pt.isel.odin.service.department.error.GetDepartmentError
import pt.isel.odin.service.department.error.SaveUpdateDepartmentError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class DepartmentServiceTest {

    @Autowired
    private lateinit var departmentService: DepartmentService

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Get department by ID`() {
        // given: a saved Department instance
        val department = Department(name = "Science")
        departmentRepository.save(department)

        // when: retrieving the department by ID
        val result = departmentService.getById(department.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(department, (result as Success).value)
    }

    @Test
    fun `Get department by non-existent ID`() {
        // when: retrieving the department by non-existent ID
        val result = departmentService.getById(999)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetDepartmentError.NotFoundDepartment, (result as Failure).value)
    }

    @Test
    fun `Get all departments`() {
        // given: multiple saved Department instances
        val department1 = Department(name = "Science")
        val department2 = Department(name = "Arts")
        departmentRepository.save(department1)
        departmentRepository.save(department2)

        // when: retrieving all departments
        val result = departmentService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(department1, department2), (result as Success).value)
    }

    @Test
    fun `Save department`() {
        // given: a valid SaveDepartmentInputModel
        val saveDepartmentInputModel = SaveDepartmentInputModel(name = "Science")

        // when: saving the department
        val result = departmentService.save(saveDepartmentInputModel)

        // then: validate the save operation
        assertTrue(result is Success)
        val department = Department(
            id = (result as Success).value.id!!,
            name = saveDepartmentInputModel.name
        )
        assertEquals(department, result.value)
    }

    @Test
    fun `Save department with duplicate name`() {
        // given: a SaveDepartmentInputModel with an existing name
        val saveDepartmentInputModel = SaveDepartmentInputModel(name = "Science")
        val existingDepartment = Department(name = saveDepartmentInputModel.name)
        departmentRepository.save(existingDepartment)

        // when: saving the department with duplicate name
        val result = departmentService.save(saveDepartmentInputModel)

        // then: validate the failure due to duplicate name
        assertTrue(result is Failure)
        assertEquals(SaveUpdateDepartmentError.AlreadyExistsDepartment, (result as Failure).value)
    }

    @Test
    fun `Save department with invalid name`() {
        // given: a SaveDepartmentInputModel with an existing name
        val saveDepartmentInputModel = SaveDepartmentInputModel(name = "")

        // when: saving the department with duplicate name
        val result = departmentService.save(saveDepartmentInputModel)

        // then: validate the failure due to duplicate name
        assertTrue(result is Failure)
        assertEquals(SaveUpdateDepartmentError.IncorrectNameDepartment, (result as Failure).value)
    }



    @Test
    fun `Update department`() {
        // given: a valid UpdateDepartmentInputModel and existing department
        val existingDepartment = Department(name = "Science")
        val department = departmentRepository.save(existingDepartment)
        val updateDepartmentInputModel =
            UpdateDepartmentInputModel(
                id = department.id!!,
                name = "Natural Science"
            )
        val updatedDepartment =
            existingDepartment.copy(
                id = department.id!!,
                name = updateDepartmentInputModel.name
            )

        // when: updating the department
        val result = departmentService.update(updateDepartmentInputModel)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedDepartment, (result as Success).value)
    }

    @Test
    fun `Update non-existent department`() {
        // given: an UpdateDepartmentInputModel for a non-existent department
        val updateDepartmentInputModel = UpdateDepartmentInputModel(id = 1, name = "Natural Science")

        // when: updating the non-existent department
        val result = departmentService.update(updateDepartmentInputModel)

        // then: validate the failure due to non-existent department
        assertTrue(result is Failure)
        assertEquals(SaveUpdateDepartmentError.NotFoundDepartment, (result as Failure).value)
    }

    @Test
    fun `Update department with invalid name`() {
        // given: an UpdateDepartmentInputModel for a non-existent department
        val updateDepartmentInputModel = UpdateDepartmentInputModel(id = 1, name = "")

        // when: updating the non-existent department
        val result = departmentService.update(updateDepartmentInputModel)

        // then: validate the failure due to non-existent department
        assertTrue(result is Failure)
        assertEquals(SaveUpdateDepartmentError.IncorrectNameDepartment, (result as Failure).value)
    }

    @Test
    fun `Delete department`() {
        // given: an existing department ID
        val department = Department(name = "Science")
        departmentRepository.save(department)

        // when: deleting the department
        val result = departmentService.delete(department.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(department, (result as Success).value)

        // and: verify the delete operation
        val deletedDepartment = departmentRepository.findById(department.id!!)
        assertTrue(deletedDepartment.isEmpty)
    }

    @Test
    fun `Delete non-existent department`() {
        // when: deleting the non-existent department
        val result = departmentService.delete(1)

        // then: validate the failure due to non-existent department
        assertTrue(result is Failure)
        assertEquals(DeleteDepartmentError.NotFoundDepartment, (result as Failure).value)
    }
}
