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
import pt.isel.odin.model.Module

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
        val department = Department(name = "Engineering")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Software Engineering", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Algorithms", fieldStudy = savedFieldStudy, tier = 2)

        // when: saving the module
        val savedModule = moduleRepository.save(module)

        // then: validate the save operation
        assertNotNull(savedModule.id)
        assertEquals("Algorithms", savedModule.name)
        assertEquals(2, savedModule.tier)
        assertEquals(savedFieldStudy.id, savedModule.fieldStudy.id)
    }

    @Test
    fun `Find Module by ID`() {
        // given: a saved Department instance, a saved FieldStudy instance, and a saved Module instance
        val department = Department(name = "Science")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = savedFieldStudy, tier = 3)
        val savedModule = moduleRepository.save(module)

        // when: retrieving the module by ID
        val retrievedModule = moduleRepository.findById(savedModule.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedModule)
        assertEquals("Quantum Mechanics", retrievedModule?.name)
        assertEquals(3, retrievedModule?.tier)
        assertEquals(savedFieldStudy.id, retrievedModule?.fieldStudy?.id)
    }

    @Test
    fun `Find Module by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the module by non-existent ID
        val retrievedModule = moduleRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedModule)
    }

    @Test
    fun `Find Module by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the module by negative ID
        val retrievedModule = moduleRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedModule)
    }

    @Test
    fun `Find all Modules`() {
        // given: multiple saved Module instances
        val department = Department(name = "Arts")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "History", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module1 = Module(name = "Ancient History", fieldStudy = savedFieldStudy)
        val module2 = Module(name = "Modern History", fieldStudy = savedFieldStudy)
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
        val department = Department(name = "Business")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Management", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Marketing", fieldStudy = savedFieldStudy)
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
        val department = Department(name = "Law")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Criminal Law", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Forensic Science", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)

        // when: deleting the module
        moduleRepository.deleteById(savedModule.id!!)

        // then: validate the delete operation
        val deletedModule = moduleRepository.findById(savedModule.id!!).orElse(null)
        assertNull(deletedModule)
    }
}
