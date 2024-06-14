package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Section
import pt.isel.odin.utils.TestData

@DataJpaTest
@Transactional
class TechRepositoryTest {

    @Autowired
    lateinit var techRepository: TechRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var moduleRepository: ModuleRepository

    @Test
    fun `Save Tech`() {
        // given: a Role instance, a User instance as a teacher, a Department, a FieldStudy, a Module instance, and a Tech instance
        val savedRole = roleRepository.save(TestData.role1)
        val savedTeacher = userRepository.save(TestData.user1.copy(role = savedRole))
        val savedSection = createSection()
        val tech = TestData.tech1.copy(teacher = savedTeacher, section = savedSection)

        // when: saving the tech
        val savedTech = techRepository.save(tech)

        // then: validate the save operation
        assertNotNull(savedTech.id)
        assertEquals(savedTeacher.id, savedTech.teacher.id)
        assertEquals(savedSection.id, savedTech.section.id)
        assertEquals("Tech summary", savedTech.summary)
    }

    @Test
    fun `Find Tech by ID`() {
        // given: a saved Role instance, a saved User instance as a teacher, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Tech instance
        val savedRole = roleRepository.save(TestData.role1)
        val savedTeacher = userRepository.save(TestData.user1.copy(role = savedRole))
        val savedSection = createSection()
        val tech = TestData.tech2.copy(teacher = savedTeacher, section = savedSection)
        val savedTech = techRepository.save(tech)

        // when: retrieving the tech by ID
        val retrievedTech = techRepository.findById(savedTech.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedTech)
        assertEquals(savedTeacher.id, retrievedTech?.teacher?.id)
        assertEquals(savedSection.id, retrievedTech?.section?.id)
        assertEquals("Algebra Tech summary", retrievedTech?.summary)
    }

    @Test
    fun `Find Tech by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the tech by non-existent ID
        val retrievedTech = techRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedTech)
    }

    @Test
    fun `Find Tech by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the tech by negative ID
        val retrievedTech = techRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedTech)
    }

    @Test
    fun `Find all Techs`() {
        // given: multiple saved Tech instances
        val savedRole = roleRepository.save(TestData.role1)
        val savedTeacher = userRepository.save(TestData.user1.copy(role = savedRole))
        val savedSection = createSection()
        val tech1 = TestData.tech3.copy(teacher = savedTeacher, section = savedSection)
        val tech2 = TestData.tech4.copy(teacher = savedTeacher, section = savedSection)
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
        val savedRole = roleRepository.save(TestData.role1)
        val savedTeacher = userRepository.save(TestData.user1.copy(role = savedRole))
        val savedSection = createSection()
        val tech = TestData.tech5.copy(teacher = savedTeacher, section = savedSection)
        val savedTech = techRepository.save(tech)

        // when: updating the tech's summary
        val updatedTech = techRepository.save(savedTech.copy(summary = "Updated Botany Tech"))

        // then: validate the update operation
        assertEquals("Updated Botany Tech", updatedTech.summary)
    }

    @Test
    fun `Delete Tech`() {
        // given: a saved Role instance, a saved User instance as a teacher, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Tech instance
        val savedRole = roleRepository.save(TestData.role1)
        val savedTeacher = userRepository.save(TestData.user1.copy(role = savedRole))
        val savedSection = createSection()
        val tech = TestData.tech6.copy(teacher = savedTeacher, section = savedSection)
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
        val savedRole = roleRepository.save(TestData.role2)
        val savedStudent1 = userRepository.save(TestData.user2.copy(role = savedRole))
        val savedStudent2 = userRepository.save(TestData.user3.copy(role = savedRole))
        val savedTeacherRole = roleRepository.save(TestData.role1)
        val savedTeacher = userRepository.save(TestData.user1.copy(role = savedTeacherRole))
        val savedSection = createSection()
        val tech = TestData.tech7.copy(teacher = savedTeacher, section = savedSection, missTech = mutableListOf(savedStudent1, savedStudent2))

        // when: saving the tech
        val savedTech = techRepository.save(tech)

        // then: validate the save operation
        assertNotNull(savedTech.id)
        assertEquals(2, savedTech.missTech.size)
        assertTrue(savedTech.missTech.contains(savedStudent1))
        assertTrue(savedTech.missTech.contains(savedStudent2))
    }

    fun createSection(): Section {
        val savedDepartment = departmentRepository.save(TestData.department1)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy1.copy(department = savedDepartment))
        val savedModule = moduleRepository.save(TestData.module1.copy(fieldStudy = savedFieldStudy))
        return sectionRepository.save(TestData.section1.copy(module = savedModule))
    }
}
