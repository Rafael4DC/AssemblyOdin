package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechInputModel
import pt.isel.odin.model.Role
import pt.isel.odin.model.Section
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.tech.TechService
import pt.isel.odin.service.tech.error.DeleteTechError
import pt.isel.odin.service.tech.error.GetTechError
import pt.isel.odin.service.tech.error.SaveUpdateTechError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import pt.isel.odin.utils.TestData
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class TechServiceTest {

    @Autowired
    private lateinit var techService: TechService

    @Autowired
    private lateinit var techRepository: TechRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var sectionRepository: SectionRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    @Autowired
    private lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    private lateinit var moduleRepository: ModuleRepository

    @Test
    fun `Get tech by ID`() {
        // given: a saved Tech instance
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val tech = Tech(teacher = user, section = createSection(), date = LocalDateTime.now(), summary = "Tech Summary")
        techRepository.save(tech)

        // when: retrieving the tech by ID
        val result = techService.getById(tech.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(tech, (result as Success).value)
    }

    @Test
    fun `Get tech by non-existent ID`() {
        // when: retrieving the tech by non-existent ID
        val result = techService.getById(1)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetTechError.NotFoundTech, (result as Failure).value)
    }

    @Test
    fun `Get all techs`() {
        // given: multiple saved Tech instances
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val tech1 = Tech(teacher = user, section = section, date = LocalDateTime.now(), summary = "Tech Summary 1")
        val tech2 = Tech(teacher = user, section = section, date = LocalDateTime.now(), summary = "Tech Summary 2")
        techRepository.save(tech1)
        techRepository.save(tech2)

        // when: retrieving all techs
        val result = techService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(tech1, tech2), (result as Success).value)
    }

    @Test
    fun `Save tech`() {
        // given: a valid SaveTechInputModel
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val saveTechInputModel = SaveTechInputModel(
            teacher = user.id!!,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Tech Summary",
            missTech = mutableListOf(user.id!!)
        )

        // when: saving the tech
        val result = techService.save(saveTechInputModel, user.email)

        // then: validate the save operation
        assertTrue(result is Success)
        val tech = Tech(
            id = (result as Success).value.id!!,
            teacher = user,
            section = section,
            date = LocalDateTime.parse(saveTechInputModel.date),
            summary = saveTechInputModel.summary,
            missTech = mutableListOf(user)
        )
        assertEquals(tech, result.value)
    }

    @Test
    fun `Save tech with empty list`() {
        // given: a valid SaveTechInputModel
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val saveTechInputModel = SaveTechInputModel(
            teacher = user.id!!,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Tech Summary"
        )

        // when: saving the tech
        val result = techService.save(saveTechInputModel, user.email)

        // then: validate the save operation
        assertTrue(result is Success)
        val tech = Tech(
            id = (result as Success).value.id!!,
            teacher = user,
            section = section,
            date = LocalDateTime.parse(saveTechInputModel.date),
            summary = saveTechInputModel.summary,
            missTech = mutableListOf()
        )
        assertEquals(tech, result.value)
    }

    @Test
    fun `Save tech with non-existent user`() {
        // given: a SaveTechInputModel with a non-existent user
        val section = createSection()
        val saveTechInputModel = SaveTechInputModel(
            teacher = 9999,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Tech Summary",
            missTech = emptyList()
        )

        // when: saving the tech
        val result = techService.save(saveTechInputModel, "nonexistent@example.com")

        // then: validate the failure due to non-existent user
        assertTrue(result is Failure)
        assertEquals(SaveUpdateTechError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Save tech with non-existent module`() {
        // given: a SaveTechInputModel with a non-existent module
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val saveTechInputModel = SaveTechInputModel(
            teacher = user.id!!,
            section = 9999,
            date = LocalDateTime.now().toString(),
            summary = "Tech Summary",
            missTech = emptyList()
        )

        // when: saving the tech
        val result = techService.save(saveTechInputModel, user.email)

        // then: validate the failure due to non-existent module
        assertTrue(result is Failure)
        assertEquals(SaveUpdateTechError.NotFoundSection, (result as Failure).value)
    }

    @Test
    fun `Update tech`() {
        // given: a valid UpdateTechInputModel and existing tech
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val existingTech = Tech(teacher = user, section = section, date = LocalDateTime.now(), summary = "Tech Summary")
        val tech = techRepository.save(existingTech)
        val updateTechInputModel = UpdateTechInputModel(
            id = tech.id!!,
            teacher = user.id!!,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Updated Tech Summary",
            missTech = emptyList()
        )
        val updatedTech = existingTech.copy(
            id = tech.id!!,
            teacher = user,
            section = section,
            date = LocalDateTime.parse(updateTechInputModel.date),
            summary = updateTechInputModel.summary
        )

        // when: updating the tech
        val result = techService.update(updateTechInputModel, user.email)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedTech, (result as Success).value)
    }

    @Test
    fun `Update tech with empty list of miss students`() {
        // given: a valid UpdateTechInputModel and existing tech
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val existingTech = Tech(
            teacher = user,
            section = section,
            date = LocalDateTime.now(),
            summary = "Tech Summary",
            missTech = mutableListOf(user)
        )
        val tech = techRepository.save(existingTech)
        val updateTechInputModel = UpdateTechInputModel(
            id = tech.id!!,
            teacher = user.id!!,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Updated Tech Summary",
            missTech = emptyList()
        )
        val updatedTech = existingTech.copy(
            id = tech.id!!,
            teacher = user,
            section = section,
            date = LocalDateTime.parse(updateTechInputModel.date),
            summary = updateTechInputModel.summary,
            missTech = mutableListOf()
        )

        // when: updating the tech
        val result = techService.update(updateTechInputModel, user.email)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedTech, (result as Success).value)
    }

    @Test
    fun `Update tech adding one student to miss students`() {
        // given: a valid UpdateTechInputModel and existing tech
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val existingTech = Tech(
            teacher = user,
            section = section,
            date = LocalDateTime.now(),
            summary = "Tech Summary",
            missTech = mutableListOf()
        )
        val tech = techRepository.save(existingTech)
        val updateTechInputModel = UpdateTechInputModel(
            id = tech.id!!,
            teacher = user.id!!,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Updated Tech Summary",
            missTech = listOf(user.id!!)
        )
        val updatedTech = existingTech.copy(
            id = tech.id!!,
            teacher = user,
            section = section,
            date = LocalDateTime.parse(updateTechInputModel.date),
            summary = updateTechInputModel.summary,
            missTech = mutableListOf(user)
        )

        // when: updating the tech
        val result = techService.update(updateTechInputModel, user.email)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedTech, (result as Success).value)
    }

    @Test
    fun `Update non-existent tech`() {
        // given: an UpdateTechInputModel for a non-existent tech
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val updateTechInputModel = UpdateTechInputModel(
            id = 1,
            teacher = user.id!!,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Updated Tech Summary",
            missTech = emptyList()
        )

        // when: updating the non-existent tech
        val result = techService.update(updateTechInputModel, user.email)

        // then: validate the failure due to non-existent tech
        assertTrue(result is Failure)
        assertEquals(SaveUpdateTechError.NotFoundTech, (result as Failure).value)
    }

    @Test
    fun `Update tech with non-existent user`() {
        // given: a valid UpdateTechInputModel with a non-existent user
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val existingTech = Tech(teacher = user, section = section, date = LocalDateTime.now(), summary = "Tech Summary")
        techRepository.save(existingTech)
        val updateTechInputModel = UpdateTechInputModel(
            id = existingTech.id!!,
            teacher = 9999,
            section = section.id!!,
            date = LocalDateTime.now().toString(),
            summary = "Updated Tech Summary",
            missTech = emptyList()
        )

        // when: updating the tech
        val result = techService.update(updateTechInputModel, user.email)

        // then: validate the failure due to non-existent user
        assertTrue(result is Failure)
        assertEquals(SaveUpdateTechError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Update tech with non-existent module`() {
        // given: a valid UpdateTechInputModel with a non-existent module
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val existingTech = Tech(teacher = user, section = section, date = LocalDateTime.now(), summary = "Tech Summary")
        techRepository.save(existingTech)
        val updateTechInputModel = UpdateTechInputModel(
            id = existingTech.id!!,
            teacher = user.id!!,
            section = 9999,
            date = LocalDateTime.now().toString(),
            summary = "Updated Tech Summary",
            missTech = emptyList()
        )

        // when: updating the tech
        val result = techService.update(updateTechInputModel, user.email)

        // then: validate the failure due to non-existent module
        assertTrue(result is Failure)
        assertEquals(SaveUpdateTechError.NotFoundSection, (result as Failure).value)
    }

    @Test
    fun `Delete tech`() {
        // given: an existing tech ID
        val role = Role(name = "Teacher")
        roleRepository.save(role)
        val user = User(email = "teacher@example.com", username = "teacher", role = role)
        userRepository.save(user)
        val section = createSection()
        val tech = Tech(teacher = user, section = section, date = LocalDateTime.now(), summary = "Tech Summary")
        techRepository.save(tech)

        // when: deleting the tech
        val result = techService.delete(tech.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(tech, (result as Success).value)

        // and: verify the delete operation
        val deletedTech = techRepository.findById(tech.id!!)
        assertTrue(deletedTech.isEmpty)
    }

    @Test
    fun `Delete non-existent tech`() {
        // when: deleting the non-existent tech
        val result = techService.delete(1)

        // then: validate the failure due to non-existent tech
        assertTrue(result is Failure)
        assertEquals(DeleteTechError.NotFoundTech, (result as Failure).value)
    }

    fun createSection(): Section {
        val savedDepartment = departmentRepository.save(TestData.department1)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy1.copy(department = savedDepartment))
        val savedModule = moduleRepository.save(TestData.module1.copy(fieldStudy = savedFieldStudy))
        return sectionRepository.save(TestData.section1.copy(module = savedModule))
    }
}
