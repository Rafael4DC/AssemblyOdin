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
import pt.isel.odin.model.user.User
import pt.isel.odin.utils.TestData

@DataJpaTest
@Transactional
class SectionRepositoryTest {

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var moduleRepository: ModuleRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Test
    fun `Save Section`() {
        // given: a Section instance
        val savedDepartment = departmentRepository.save(TestData.department1)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy1.copy(department = savedDepartment))
        moduleRepository.save(TestData.module1.copy(fieldStudy = savedFieldStudy))
        val section = TestData.section1

        // when: saving the section
        val savedSection = sectionRepository.save(section)

        // then: validate the save operation
        assertNotNull(savedSection.id)
        assertEquals(TestData.section1.name, savedSection.name)
        assertEquals(TestData.section1.summary, savedSection.summary)
    }

    @Test
    fun `Find Section by ID`() {
        // given: a saved Section instance
        val savedDepartment = departmentRepository.save(TestData.department2)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy2.copy(department = savedDepartment))
        moduleRepository.save(TestData.module2.copy(fieldStudy = savedFieldStudy))
        val section = TestData.section2
        val savedSection = sectionRepository.save(section)

        // when: retrieving the section by ID
        val retrievedSection = sectionRepository.findById(savedSection.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedSection)
        assertEquals(TestData.section2.name, retrievedSection?.name)
        assertEquals(TestData.section2.summary, retrievedSection?.summary)
    }

    @Test
    fun `Find Section by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the section by non-existent ID
        val retrievedSection = sectionRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedSection)
    }

    @Test
    fun `Find Section by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the section by negative ID
        val retrievedSection = sectionRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedSection)
    }

    @Test
    fun `Find all Sections`() {
        val savedDepartment = departmentRepository.save(TestData.department3)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy3.copy(department = savedDepartment))
        val savedModule = moduleRepository.save(TestData.module3.copy(fieldStudy = savedFieldStudy))
        val section1 = TestData.section3.copy(module = savedModule)
        val section2 = TestData.section3.copy(name = "Section 2", module = savedModule)
        sectionRepository.save(section1)
        sectionRepository.save(section2)

        // when: retrieving all sections
        val sections = sectionRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, sections.size)
    }

    @Test
    fun `Update Section`() {
        // given: a saved Section instance
        val savedDepartment = departmentRepository.save(TestData.department4)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy4.copy(department = savedDepartment))
        moduleRepository.save(TestData.module5.copy(fieldStudy = savedFieldStudy))
        val section = TestData.section5
        val savedSection = sectionRepository.save(section)

        // when: updating the section's name and summary
        val updatedSection = sectionRepository.save(savedSection.copy(name = "New Name", summary = "New summary."))

        // then: validate the update operation
        assertEquals("New Name", updatedSection.name)
        assertEquals("New summary.", updatedSection.summary)
    }

    @Test
    fun `Delete Section`() {
        // given: a saved Section instance
        val savedDepartment = departmentRepository.save(TestData.department5)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy5.copy(department = savedDepartment))
        moduleRepository.save(TestData.module6.copy(fieldStudy = savedFieldStudy))
        val section = TestData.section6
        val savedSection = sectionRepository.save(section)

        // when: deleting the section
        sectionRepository.deleteById(savedSection.id!!)

        // then: validate the delete operation
        val deletedSection = sectionRepository.findById(savedSection.id!!).orElse(null)
        assertNull(deletedSection)
    }

    @Test
    fun `Save Section with students`() {
        // given: a Role instance, multiple User instances, and a Section instance with students
        val savedDepartment = departmentRepository.save(TestData.department5)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy5.copy(department = savedDepartment))
        moduleRepository.save(TestData.module6.copy(fieldStudy = savedFieldStudy))
        val savedRole = roleRepository.save(TestData.role1)
        val user1 = User(email = TestData.user1.email, username = TestData.user1.username, role = savedRole)
        val user2 = User(email = TestData.user2.email, username = TestData.user2.username, role = savedRole)
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        val section = Section(
            name = TestData.section7.name,
            summary = TestData.section7.summary,
            module = TestData.module6,
            students = mutableListOf(savedUser1, savedUser2)
        )

        // when: saving the section
        val savedSection = sectionRepository.save(section)

        // then: validate the save operation
        assertNotNull(savedSection.id)
        assertEquals(2, savedSection.students.size)
        assertTrue(savedSection.students.contains(savedUser1))
        assertTrue(savedSection.students.contains(savedUser2))
    }
}
