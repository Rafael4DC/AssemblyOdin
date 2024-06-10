package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
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
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

@DataJpaTest
@Transactional
class TechRepositoryTest {

    @Autowired
    lateinit var techRepository: TechRepository

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
    fun `Save Tech`() {
        // given: a Role instance, a User instance as a teacher, a Department, a FieldStudy, a Module instance, and a Tech instance
        val role = Role(name = "Teacher")
        val savedRole = roleRepository.save(role)
        val teacher = User(email = "teacher@example.com", username = "teacher", role = savedRole)
        val savedTeacher = userRepository.save(teacher)
        val department = Department(name = "Science")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Computer Science", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Software Engineering", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val tech =
            Tech(teacher = savedTeacher, module = savedModule, date = LocalDateTime.now(), summary = "Tech summary")

        // when: saving the tech
        val savedTech = techRepository.save(tech)

        // then: validate the save operation
        assertNotNull(savedTech.id)
        assertEquals(savedTeacher.id, savedTech.teacher.id)
        assertEquals(savedModule.id, savedTech.module.id)
        assertEquals("Tech summary", savedTech.summary)
    }

    @Test
    fun `Find Tech by ID`() {
        // given: a saved Role instance, a saved User instance as a teacher, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Tech instance
        val role = Role(name = "Teacher")
        val savedRole = roleRepository.save(role)
        val teacher = User(email = "teacher@example.com", username = "teacher", role = savedRole)
        val savedTeacher = userRepository.save(teacher)
        val department = Department(name = "Mathematics")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Algebra", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Abstract Algebra", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val tech = Tech(
            teacher = savedTeacher,
            module = savedModule,
            date = LocalDateTime.now(),
            summary = "Algebra Tech summary"
        )
        val savedTech = techRepository.save(tech)

        // when: retrieving the tech by ID
        val retrievedTech = techRepository.findById(savedTech.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedTech)
        assertEquals(savedTeacher.id, retrievedTech?.teacher?.id)
        assertEquals(savedModule.id, retrievedTech?.module?.id)
        assertEquals("Algebra Tech summary", retrievedTech?.summary)
    }

    @Test
    fun `Find Tech by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the tech by non-existent ID
        val retrievedTech = techRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedTech)
    }

    @Test
    fun `Find Tech by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the tech by negative ID
        val retrievedTech = techRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedTech)
    }

    @Test
    fun `Find all Techs`() {
        // given: multiple saved Tech instances
        val role = Role(name = "Teacher")
        val savedRole = roleRepository.save(role)
        val teacher = User(email = "teacher@example.com", username = "teacher", role = savedRole)
        val savedTeacher = userRepository.save(teacher)
        val department = Department(name = "Physics")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Quantum Physics", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val tech1 =
            Tech(teacher = savedTeacher, module = savedModule, date = LocalDateTime.now(), summary = "Physics Tech 1")
        val tech2 =
            Tech(teacher = savedTeacher, module = savedModule, date = LocalDateTime.now(), summary = "Physics Tech 2")
        techRepository.save(tech1)
        techRepository.save(tech2)

        // when: retrieving all techs
        val techs = techRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, techs.size)
    }

    @Test
    fun `Update Tech`() {
        // given: a saved Role instance, a saved User instance as a teacher, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Tech instance
        val role = Role(name = "Teacher")
        val savedRole = roleRepository.save(role)
        val teacher = User(email = "teacher@example.com", username = "teacher", role = savedRole)
        val savedTeacher = userRepository.save(teacher)
        val department = Department(name = "Biology")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Botany", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Plant Biology", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val tech =
            Tech(teacher = savedTeacher, module = savedModule, date = LocalDateTime.now(), summary = "Botany Tech")
        val savedTech = techRepository.save(tech)

        // when: updating the tech's summary
        val updatedTech = techRepository.save(savedTech.copy(summary = "Updated Botany Tech"))

        // then: validate the update operation
        assertEquals("Updated Botany Tech", updatedTech.summary)
    }

    @Test
    fun `Delete Tech`() {
        // given: a saved Role instance, a saved User instance as a teacher, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Tech instance
        val role = Role(name = "Teacher")
        val savedRole = roleRepository.save(role)
        val teacher = User(email = "teacher@example.com", username = "teacher", role = savedRole)
        val savedTeacher = userRepository.save(teacher)
        val department = Department(name = "Chemistry")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Organic Chemistry", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Organic Reactions", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val tech = Tech(
            teacher = savedTeacher,
            module = savedModule,
            date = LocalDateTime.now(),
            summary = "Organic Chemistry Tech"
        )
        val savedTech = techRepository.save(tech)

        // when: deleting the tech
        techRepository.deleteById(savedTech.id!!)

        // then: validate the delete operation
        val deletedTech = techRepository.findById(savedTech.id!!).orElse(null)
        assertNull(deletedTech)
    }

    @Test
    fun `Save Tech with missTech`() {
        // given: a Role instance, multiple User instances as missTech, a User instance as a teacher, a Department, a FieldStudy, a Module instance, and a Tech instance
        val role = Role(name = "Student")
        val savedRole = roleRepository.save(role)
        val student1 = User(email = "student1@example.com", username = "student1", role = savedRole)
        val student2 = User(email = "student2@example.com", username = "student2", role = savedRole)
        val savedStudent1 = userRepository.save(student1)
        val savedStudent2 = userRepository.save(student2)
        val teacherRole = Role(name = "Teacher")
        val savedTeacherRole = roleRepository.save(teacherRole)
        val teacher = User(email = "teacher@example.com", username = "teacher", role = savedTeacherRole)
        val savedTeacher = userRepository.save(teacher)
        val department = Department(name = "History")
        val savedDepartment = departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "World History", department = savedDepartment)
        val savedFieldStudy = fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Medieval History", fieldStudy = savedFieldStudy)
        val savedModule = moduleRepository.save(module)
        val tech = Tech(
            teacher = savedTeacher,
            module = savedModule,
            date = LocalDateTime.now(),
            summary = "History Tech",
            missTech = mutableListOf(savedStudent1, savedStudent2)
        )

        // when: saving the tech
        val savedTech = techRepository.save(tech)

        // then: validate the save operation
        assertNotNull(savedTech.id)
        assertEquals(2, savedTech.missTech.size)
        assertTrue(savedTech.missTech.contains(savedStudent1))
        assertTrue(savedTech.missTech.contains(savedStudent2))
    }
}
