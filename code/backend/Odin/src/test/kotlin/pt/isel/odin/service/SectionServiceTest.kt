package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pt.isel.odin.http.controllers.section.models.SaveSectionInputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionInputModel
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.model.Role
import pt.isel.odin.model.Section
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.section.SectionService
import pt.isel.odin.service.section.error.DeleteSectionError
import pt.isel.odin.service.section.error.GetSectionError
import pt.isel.odin.service.section.error.SaveUpdateSectionError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class SectionServiceTest {

    @Autowired
    private lateinit var sectionService: SectionService

    @Autowired
    private lateinit var sectionRepository: SectionRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    @Autowired
    private lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    private lateinit var moduleRepository: ModuleRepository

    @Test
    fun `Get section by ID`() {
        // given: a saved Section instance
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val section =
            Section(name = "Section A", module = module, students = mutableListOf(user))
        sectionRepository.save(section)

        // when: retrieving the section by ID
        val result = sectionService.getById(section.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(section, (result as Success).value)
    }

    @Test
    fun `Get section by non-existent ID`() {
        // when: retrieving the section by non-existent ID
        val result = sectionService.getById(1)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetSectionError.NotFoundSection, (result as Failure).value)
    }

    @Test
    fun `Get all sections`() {
        // given: multiple saved Section instances
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val section1 =
            Section(name = "Section A", module = module, students = mutableListOf(user))
        val section2 =
            Section(name = "Section B", module = module, students = mutableListOf(user))
        sectionRepository.save(section1)
        sectionRepository.save(section2)

        // when: retrieving all sections
        val result = sectionService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(section1, section2), (result as Success).value)
    }

    @Test
    fun `Save section`() {
        // given: a valid SaveSectionInputModel
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val saveSectionInputModel =
            SaveSectionInputModel(
                name = "Section A",
                module = module.id!!,
                students = listOf(user.id!!)
            )

        // when: saving the section
        val result = sectionService.save(saveSectionInputModel)

        // then: validate the save operation
        assertTrue(result is Success)
        val section = Section(
            id = (result as Success).value.id!!,
            name = saveSectionInputModel.name,
            students = mutableListOf(user),
            module = module
        )
        assertEquals(section, result.value)
    }

    @Test
    fun `Save section with empty students list`() {
        // given: a valid SaveSectionInputModel
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val saveSectionInputModel =
            SaveSectionInputModel(name = "Section A", module = module.id!!)

        // when: saving the section
        val result = sectionService.save(saveSectionInputModel)

        // then: validate the save operation
        assertTrue(result is Success)
        val section = Section(
            id = (result as Success).value.id!!,
            name = saveSectionInputModel.name,
            module = module
        )
        assertEquals(section, result.value)
    }

    @Test
    fun `Save section with duplicate name`() {
        // given: a SaveSectionInputModel with an existing name
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val saveSectionInputModel =
            SaveSectionInputModel(
                name = "Section A",
                module = module.id!!,
                students = listOf(user.id!!)
            )
        val existingSection = Section(
            name = saveSectionInputModel.name,
            module = module,
            students = mutableListOf(user)
        )
        sectionRepository.save(existingSection)

        // when: saving the section with duplicate name
        val result = sectionService.save(saveSectionInputModel)

        // then: validate the failure due to duplicate name
        assertTrue(result is Failure)
        assertEquals(SaveUpdateSectionError.AlreadyExistsSection, (result as Failure).value)
    }

    @Test
    fun `Update section`() {
        // given: a valid UpdateSectionInputModel and existing section
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val existingSection =
            Section(name = "Section A", module = module, students = mutableListOf(user))
        val section = sectionRepository.save(existingSection)
        val updateSectionInputModel = UpdateSectionInputModel(
            id = section.id!!,
            name = "Section A Updated",
            module = module.id!!,
            students = listOf(user.id!!)
        )
        val updatedSection = existingSection.copy(
            id = section.id!!,
            name = updateSectionInputModel.name,
        )

        // when: updating the section
        val result = sectionService.update(updateSectionInputModel)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedSection, (result as Success).value)
    }

    @Test
    fun `Update section list of students`() {
        // given: a valid UpdateSectionInputModel and existing section
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val existingSection =
            Section(name = "Section A", module = module, students = mutableListOf(user))
        val section = sectionRepository.save(existingSection)
        val updateSectionInputModel = UpdateSectionInputModel(
            id = section.id!!,
            name = existingSection.name,
            module = module.id!!,
        )
        val updatedSection = existingSection.copy(
            id = section.id!!,
            name = updateSectionInputModel.name,
            students = mutableListOf()
        )

        // when: updating the section
        val result = sectionService.update(updateSectionInputModel)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedSection, (result as Success).value)
    }

    @Test
    fun `Update non-existent section`() {
        // given: an UpdateSectionInputModel for a non-existent section
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val updateSectionInputModel = UpdateSectionInputModel(
            id = 1,
            name = "Section A Updated",
            module = module.id!!,
            students = listOf(user.id!!)
        )

        // when: updating the non-existent section
        val result = sectionService.update(updateSectionInputModel)

        // then: validate the failure due to non-existent section
        assertTrue(result is Failure)
        assertEquals(SaveUpdateSectionError.NotFoundSection, (result as Failure).value)
    }

    @Test
    fun `Delete section`() {
        // given: an existing section ID
        val role = Role(name = "User")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val section =
            Section(name = "Section A", module = module, students = mutableListOf(user))
        sectionRepository.save(section)

        // when: deleting the section
        val result = sectionService.delete(section.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(section, (result as Success).value)

        // and: verify the delete operation
        val deletedSection = sectionRepository.findById(section.id!!)
        assertTrue(deletedSection.isEmpty)
    }

    @Test
    fun `Delete non-existent section`() {
        // when: deleting the non-existent section
        val result = sectionService.delete(1)

        // then: validate the failure due to non-existent section
        assertTrue(result is Failure)
        assertEquals(DeleteSectionError.NotFoundSection, (result as Failure).value)
    }
}
