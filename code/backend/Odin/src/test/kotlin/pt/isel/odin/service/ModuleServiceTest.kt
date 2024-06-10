package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pt.isel.odin.http.controllers.module.models.SaveModuleInputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleInputModel
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.service.module.ModuleService
import pt.isel.odin.service.module.error.DeleteModuleError
import pt.isel.odin.service.module.error.GetModuleError
import pt.isel.odin.service.module.error.SaveUpdateModuleError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class ModuleServiceTest {

    @Autowired
    private lateinit var moduleService: ModuleService

    @Autowired
    private lateinit var moduleRepository: ModuleRepository

    @Autowired
    private lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Get module by ID`() {
        // given: a saved Module instance
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)

        // when: retrieving the module by ID
        val result = moduleService.getById(module.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(module, (result as Success).value)
    }

    @Test
    fun `Get module by non-existent ID`() {
        // when: retrieving the module by non-existent ID
        val result = moduleService.getById(1)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetModuleError.NotFoundModule, (result as Failure).value)
    }

    @Test
    fun `Get all modules`() {
        // given: multiple saved Module instances
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module1 = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        val module2 = Module(name = "Thermodynamics", fieldStudy = fieldStudy, tier = 2)
        moduleRepository.save(module1)
        moduleRepository.save(module2)

        // when: retrieving all modules
        val result = moduleService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(module1, module2), (result as Success).value)
    }

    @Test
    fun `Save module`() {
        // given: a valid SaveModuleInputModel
        val department = Department(name = "Science")
        departmentRepository.save(department).id!!
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        val fieldStudyId = fieldStudyRepository.save(fieldStudy).id!!
        val saveModuleInputModel = SaveModuleInputModel(name = "Quantum Mechanics", fieldStudy = fieldStudyId, tier = 1)

        // when: saving the module
        val result = moduleService.save(saveModuleInputModel)

        // then: validate the save operation
        assertTrue(result is Success)
        val module = Module(
            id = (result as Success).value.id!!,
            name = saveModuleInputModel.name,
            fieldStudy = fieldStudy,
            tier = saveModuleInputModel.tier
        )
        assertEquals(module, result.value)
    }

    @Test
    fun `Save module with duplicate name`() {
        // given: a SaveModuleInputModel with an existing name
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val saveModuleInputModel =
            SaveModuleInputModel(name = "Quantum Mechanics", fieldStudy = fieldStudy.id!!, tier = 1)
        val existingModule = Module(name = saveModuleInputModel.name, fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(existingModule)

        // when: saving the module with duplicate name
        val result = moduleService.save(saveModuleInputModel)

        // then: validate the failure due to duplicate name
        assertTrue(result is Failure)
        assertEquals(SaveUpdateModuleError.AlreadyExistsModule, (result as Failure).value)
    }

    @Test
    fun `Save module with non-existent field study`() {
        // given: a SaveModuleInputModel with a non-existent field study
        val saveModuleInputModel = SaveModuleInputModel(name = "Quantum Mechanics", fieldStudy = 9999, tier = 1)

        // when: saving the module
        val result = moduleService.save(saveModuleInputModel)

        // then: validate the failure due to non-existent field study
        assertTrue(result is Failure)
        assertEquals(SaveUpdateModuleError.NotFoundFieldStudy, (result as Failure).value)
    }

    @Test
    fun `Update module`() {
        // given: a valid UpdateModuleInputModel and existing module
        val department = Department(name = "Science")
        departmentRepository.save(department).id!!
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        val fieldStudyId = fieldStudyRepository.save(fieldStudy).id!!
        val existingModule = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        val module = moduleRepository.save(existingModule)
        val updateModuleInputModel =
            UpdateModuleInputModel(
                id = module.id!!,
                name = "Advanced Quantum Mechanics",
                fieldStudy = fieldStudyId,
                tier = 2
            )
        val updatedModule =
            existingModule.copy(
                id = module.id!!,
                name = updateModuleInputModel.name,
                tier = updateModuleInputModel.tier
            )

        // when: updating the module
        val result = moduleService.update(updateModuleInputModel)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedModule, (result as Success).value)
    }

    @Test
    fun `Update non-existent module`() {
        // given: an UpdateModuleInputModel for a non-existent module
        val department = Department(name = "Science")
        departmentRepository.save(department).id!!
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        val fieldStudyId = fieldStudyRepository.save(fieldStudy).id!!
        val updateModuleInputModel =
            UpdateModuleInputModel(id = 1, name = "Advanced Quantum Mechanics", fieldStudy = fieldStudyId, tier = 2)

        // when: updating the non-existent module
        val result = moduleService.update(updateModuleInputModel)

        // then: validate the failure due to non-existent module
        assertTrue(result is Failure)
        assertEquals(SaveUpdateModuleError.NotFoundModule, (result as Failure).value)
    }

    @Test
    fun `Update module with non-existent field study`() {
        // given: a valid UpdateModuleInputModel with non-existent field study
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val existingModule = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(existingModule)
        val updateModuleInputModel = UpdateModuleInputModel(
            id = existingModule.id!!,
            name = "Advanced Quantum Mechanics",
            fieldStudy = 9999,
            tier = 2
        )

        // when: updating the module
        val result = moduleService.update(updateModuleInputModel)

        // then: validate the failure due to non-existent field study
        assertTrue(result is Failure)
        assertEquals(SaveUpdateModuleError.NotFoundFieldStudy, (result as Failure).value)
    }

    @Test
    fun `Delete module`() {
        // given: an existing module ID
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)

        // when: deleting the module
        val result = moduleService.delete(module.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(module, (result as Success).value)

        // and: verify the delete operation
        val deletedModule = moduleRepository.findById(module.id!!)
        assertTrue(deletedModule.isEmpty)
    }

    @Test
    fun `Delete non-existent module`() {
        // when: deleting the non-existent module
        val result = moduleService.delete(1)

        // then: validate the failure due to non-existent module
        assertTrue(result is Failure)
        assertEquals(DeleteModuleError.NotFoundModule, (result as Failure).value)
    }
}
