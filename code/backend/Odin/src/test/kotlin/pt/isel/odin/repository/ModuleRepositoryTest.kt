package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.utils.TestData

@DataJpaTest
@Transactional
class ModuleRepositoryTest {

    @Autowired
    lateinit var moduleRepository: ModuleRepository

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Save Module`() {
        // given: a Department instance, a FieldStudy instance, and a Module instance
        val savedDepartment = departmentRepository.save(TestData.department1)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy1.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = TestData.module1.name, fieldStudy = savedFieldStudy, tier = TestData.module1.tier)

        // when: saving the module
        val savedModule = moduleRepository.save(module)

        // then: validate the save operation
        assertNotNull(savedModule.id)
        assertEquals(TestData.module1.name, savedModule.name)
        assertEquals(TestData.module1.tier, savedModule.tier)
        assertEquals(savedFieldStudy.id, savedModule.fieldStudy.id)
    }

    @Test
    fun `Find Module by ID`() {
        // given: a saved Department instance, a saved FieldStudy instance, and a saved Module instance
        val savedDepartment = departmentRepository.save(TestData.department2)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy2.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = TestData.module2.name, fieldStudy = savedFieldStudy, tier = TestData.module2.tier)
        val savedModule = moduleRepository.save(module)

        // when: retrieving the module by ID
        val retrievedModule = moduleRepository.findById(savedModule.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedModule)
        assertEquals(TestData.module2.name, retrievedModule?.name)
        assertEquals(TestData.module2.tier, retrievedModule?.tier)
        assertEquals(savedFieldStudy.id, retrievedModule?.fieldStudy?.id)
    }

    @Test
    fun `Find Module by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the module by non-existent ID
        val retrievedModule = moduleRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedModule)
    }

    @Test
    fun `Find Module by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the module by negative ID
        val retrievedModule = moduleRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedModule)
    }

    @Test
    fun `Find all Modules`() {
        // given: multiple saved Module instances
        val savedDepartment = departmentRepository.save(TestData.department3)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy3.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module1 = Module(name = TestData.module3.name, fieldStudy = savedFieldStudy)
        val module2 = Module(name = TestData.module4.name, fieldStudy = savedFieldStudy)
        moduleRepository.save(module1)
        moduleRepository.save(module2)

        // when: retrieving all modules
        val modules = moduleRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, modules.size)
    }

    @Test
    fun `Update Module`() {
        // given: a saved Department instance, a saved FieldStudy instance, and a saved Module instance
        val savedDepartment = departmentRepository.save(TestData.department4)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy4.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = TestData.module5.name, fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)

        // when: updating the module's name and tier
        val updatedModule = moduleRepository.save(savedModule.copy(name = "Digital Marketing", tier = 4))

        // then: validate the update operation
        assertEquals("Digital Marketing", updatedModule.name)
        assertEquals(4, updatedModule.tier)
    }

    @Test
    fun `Delete Module`() {
        // given: a saved Department instance, a saved FieldStudy instance, and a saved Module instance
        val savedDepartment = departmentRepository.save(TestData.department5)
        val fieldStudy = FieldStudy(name = TestData.fieldStudy5.name, department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = TestData.module6.name, fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)

        // when: deleting the module
        moduleRepository.deleteById(savedModule.id!!)

        // then: validate the delete operation
        val deletedModule = moduleRepository.findById(savedModule.id!!).orElse(null)
        assertNull(deletedModule)
    }
}
