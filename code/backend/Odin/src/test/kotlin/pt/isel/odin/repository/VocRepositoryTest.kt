package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.model.Role
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

@DataJpaTest
@Transactional
class VocRepositoryTest {

    @Autowired
    lateinit var vocRepository: VocRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var moduleRepository: ModuleRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Test
    fun `Save Voc`() {
        // given: a Role instance, a User instance, a Department, a FieldStudy, a Module instance, and a Voc instance
        val role = Role(name = "User")
        val savedRole = roleRepository.save(role)
        val user = User(email = "user@example.com", username = "user", role = savedRole)
        val savedUser = userRepository.save(user)
        val department = Department(name = "Engineering")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Software Engineering", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Agile Development", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val voc = Voc(
            description = "Voc description",
            approved = true,
            user = savedUser,
            module = savedModule,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusDays(1)
        )

        // when: saving the voc
        val savedVoc = vocRepository.save(voc)

        // then: validate the save operation
        assertNotNull(savedVoc.id)
        assertEquals("Voc description", savedVoc.description)
        assertTrue(savedVoc.approved)
        assertEquals(savedUser.id, savedVoc.user.id)
        assertEquals(savedModule.id, savedVoc.module.id)
        assertNotNull(savedVoc.started)
        assertNotNull(savedVoc.ended)
    }

    @Test
    fun `Find Voc by ID`() {
        // given: a saved Role instance, a saved User instance, a saved Department,
        // a saved FieldStudy, a saved Module instance, and a saved Voc instance
        val role = Role(name = "User")
        val savedRole = roleRepository.save(role)
        val user = User(email = "user@example.com", username = "user", role = savedRole)
        val savedUser = userRepository.save(user)
        val department = Department(name = "Mathematics")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Algebra", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Linear Algebra", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val voc = Voc(
            description = "Algebra Voc",
            approved = true,
            user = savedUser,
            module = savedModule,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusDays(1)
        )
        val savedVoc = vocRepository.save(voc)

        // when: retrieving the voc by ID
        val retrievedVoc = vocRepository.findById(savedVoc.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedVoc)
        assertEquals("Algebra Voc", retrievedVoc?.description)
        assertTrue(retrievedVoc?.approved!!)
        assertEquals(savedUser.id, retrievedVoc.user.id)
        assertEquals(savedModule.id, retrievedVoc.module.id)
    }

    @Test
    fun `Find Voc by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the voc by non-existent ID
        val retrievedVoc = vocRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedVoc)
    }

    @Test
    fun `Find Voc by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the voc by negative ID
        val retrievedVoc = vocRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedVoc)
    }

    @Test
    fun `Find all Vocs`() {
        // given: multiple saved Voc instances
        val role = Role(name = "User")
        val savedRole = roleRepository.save(role)
        val user = User(email = "user@example.com", username = "user", role = savedRole)
        val savedUser = userRepository.save(user)
        val department = Department(name = "Science")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Mechanics", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val voc1 = Voc(
            description = "Physics Voc 1",
            approved = true,
            user = savedUser,
            module = savedModule,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusDays(1)
        )
        val voc2 = Voc(
            description = "Physics Voc 2",
            approved = false,
            user = savedUser,
            module = savedModule,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusDays(2)
        )
        vocRepository.save(voc1)
        vocRepository.save(voc2)

        // when: retrieving all vocs
        val vocs = vocRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, vocs.size)
    }

    @Test
    fun `Update Voc`() {
        // given: a saved Role instance, a saved User instance, a saved Department,
        // a saved FieldStudy, a saved Module instance, and a saved Voc instance
        val role = Role(name = "User")
        val savedRole = roleRepository.save(role)
        val user = User(email = "user@example.com", username = "user", role = savedRole)
        val savedUser = userRepository.save(user)
        val department = Department(name = "Biology")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Botany", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Plant Biology", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val voc = Voc(
            description = "Botany Voc",
            approved = true,
            user = savedUser,
            module = savedModule,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusDays(1)
        )
        val savedVoc = vocRepository.save(voc)

        // when: updating the voc's description and approval status
        val updatedVoc = vocRepository.save(savedVoc.copy(description = "Updated Botany Voc", approved = false))

        // then: validate the update operation
        assertEquals("Updated Botany Voc", updatedVoc.description)
        assertFalse(updatedVoc.approved)
    }

    @Test
    fun `Delete Voc`() {
        // given: a saved Role instance, a saved User instance, a saved Department,
        // a saved FieldStudy, a saved Module instance, and a saved Voc instance
        val role = Role(name = "User")
        val savedRole = roleRepository.save(role)
        val user = User(email = "user@example.com", username = "user", role = savedRole)
        val savedUser = userRepository.save(user)
        val department = Department(name = "History")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Medieval History", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Medieval Studies", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val voc = Voc(
            description = "History Voc",
            approved = true,
            user = savedUser,
            module = savedModule,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusDays(1)
        )
        val savedVoc = vocRepository.save(voc)

        // when: deleting the voc
        vocRepository.deleteById(savedVoc.id!!)

        // then: validate the delete operation
        val deletedVoc = vocRepository.findById(savedVoc.id!!).orElse(null)
        assertNull(deletedVoc)
    }
}
