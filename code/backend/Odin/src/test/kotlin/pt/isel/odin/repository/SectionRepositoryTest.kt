package pt.isel.odin.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Role
import pt.isel.odin.model.Section
import pt.isel.odin.model.user.User

@DataJpaTest
@Transactional
class SectionRepositoryTest {

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Test
    fun `Save Section`() {
        // given: a Section instance
        val section = Section(name = "Introduction", summary = "This is the introduction section.")

        // when: saving the section
        val savedSection = sectionRepository.save(section)

        // then: validate the save operation
        assertNotNull(savedSection.id)
        assertEquals("Introduction", savedSection.name)
        assertEquals("This is the introduction section.", savedSection.summary)
    }

    @Test
    fun `Find Section by ID`() {
        // given: a saved Section instance
        val section = Section(name = "History", summary = "This section covers history.")
        val savedSection = sectionRepository.save(section)

        // when: retrieving the section by ID
        val retrievedSection = sectionRepository.findById(savedSection.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedSection)
        assertEquals("History", retrievedSection?.name)
        assertEquals("This section covers history.", retrievedSection?.summary)
    }

    @Test
    fun `Find Section by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = 999L

        // when: retrieving the section by non-existent ID
        val retrievedSection = sectionRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedSection)
    }

    @Test
    fun `Find Section by negative ID`() {
        // given: a negative ID
        val negativeId = -1L

        // when: retrieving the section by negative ID
        val retrievedSection = sectionRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedSection)
    }

    @Test
    fun `Find all Sections`() {
        // given: multiple saved Section instances
        val section1 = Section(name = "Part 1", summary = "This is part 1.")
        val section2 = Section(name = "Part 2", summary = "This is part 2.")
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
        val section = Section(name = "Old Name", summary = "Old summary.")
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
        val section = Section(name = "To be deleted", summary = "This section will be deleted.")
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
        val role = Role(name = "Student")
        val savedRole = roleRepository.save(role)
        val user1 = User(email = "student1@example.com", username = "student1", role = savedRole)
        val user2 = User(email = "student2@example.com", username = "student2", role = savedRole)
        val savedUser1 = userRepository.save(user1)
        val savedUser2 = userRepository.save(user2)
        val section = Section(
            name = "Science",
            summary = "This section covers science.",
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
