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
import pt.isel.odin.model.Section
import pt.isel.odin.utils.TestData

@DataJpaTest
@Transactional
class VocRepositoryTest {

    @Autowired
    lateinit var vocRepository: VocRepository

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
    fun `Save Voc`() {
        // given: a Role instance, a User instance, a Department, a FieldStudy, a Module instance, and a Voc instance
        val savedRole = roleRepository.save(TestData.role2)
        val savedUser = userRepository.save(TestData.user4.copy(role = savedRole))
        val savedSection = createSection()
        val voc = TestData.voc1.copy(user = savedUser, section = savedSection)

        // when: saving the voc
        val savedVoc = vocRepository.save(voc)

        // then: validate the save operation
        assertNotNull(savedVoc.id)
        assertEquals("Voc description", savedVoc.description)
        assertTrue(savedVoc.approved)
        assertEquals(savedUser.id, savedVoc.user.id)
        assertEquals(savedSection.id, savedVoc.section.id)
        assertNotNull(savedVoc.started)
        assertNotNull(savedVoc.ended)
    }

    @Test
    fun `Find Voc by ID`() {
        // given: a saved Role instance, a saved User instance, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Voc instance
        val savedRole = roleRepository.save(TestData.role2)
        val savedUser = userRepository.save(TestData.user4.copy(role = savedRole))
        val savedSection = createSection()
        val voc = TestData.voc2.copy(user = savedUser, section = savedSection)
        val savedVoc = vocRepository.save(voc)

        // when: retrieving the voc by ID
        val retrievedVoc = vocRepository.findById(savedVoc.id!!).orElse(null)

        // then: validate the retrieval operation
        assertNotNull(retrievedVoc)
        assertEquals("Algebra Voc", retrievedVoc?.description)
        assertTrue(retrievedVoc?.approved!!)
        assertEquals(savedUser.id, retrievedVoc.user.id)
        assertEquals(savedSection.id, retrievedVoc.section.id)
    }

    @Test
    fun `Find Voc by non-existent ID`() {
        // given: a non-existent ID
        val nonExistentId = TestData.nonExistentId

        // when: retrieving the voc by non-existent ID
        val retrievedVoc = vocRepository.findById(nonExistentId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedVoc)
    }

    @Test
    fun `Find Voc by negative ID`() {
        // given: a negative ID
        val negativeId = TestData.negativeId

        // when: retrieving the voc by negative ID
        val retrievedVoc = vocRepository.findById(negativeId).orElse(null)

        // then: validate the retrieval operation
        assertNull(retrievedVoc)
    }

    @Test
    fun `Find all Vocs`() {
        // given: multiple saved Voc instances
        val savedRole = roleRepository.save(TestData.role2)
        val savedUser = userRepository.save(TestData.user4.copy(role = savedRole))
        val savedSection = createSection()
        val voc1 = TestData.voc3.copy(user = savedUser, section = savedSection)
        val voc2 = TestData.voc4.copy(user = savedUser, section = savedSection)
        vocRepository.save(voc1)
        vocRepository.save(voc2)

        // when: retrieving all vocs
        val vocs = vocRepository.findAll()

        // then: validate the retrieval operation
        assertEquals(2, vocs.size)
    }

    @Test
    fun `Update Voc`() {
        // given: a saved Role instance, a saved User instance, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Voc instance
        val savedRole = roleRepository.save(TestData.role2)
        val savedUser = userRepository.save(TestData.user4.copy(role = savedRole))
        val savedSection = createSection()
        val voc = TestData.voc5.copy(user = savedUser, section = savedSection)
        val savedVoc = vocRepository.save(voc)

        // when: updating the voc's description and approval status
        val updatedVoc = vocRepository.save(savedVoc.copy(description = "Updated Botany Voc", approved = false))

        // then: validate the update operation
        assertEquals("Updated Botany Voc", updatedVoc.description)
        assertFalse(updatedVoc.approved)
    }

    @Test
    fun `Delete Voc`() {
        // given: a saved Role instance, a saved User instance, a saved Department, a saved FieldStudy, a saved Module instance, and a saved Voc instance
        val savedRole = roleRepository.save(TestData.role2)
        val savedUser = userRepository.save(TestData.user4.copy(role = savedRole))
        val savedSection = createSection()
        val voc = TestData.voc6.copy(user = savedUser, section = savedSection)
        val savedVoc = vocRepository.save(voc)

        // when: deleting the voc
        vocRepository.deleteById(savedVoc.id!!)

        // then: validate the delete operation
        val deletedVoc = vocRepository.findById(savedVoc.id!!).orElse(null)
        assertNull(deletedVoc)
    }

    fun createSection(): Section {
        val savedDepartment = departmentRepository.save(TestData.department1)
        val savedFieldStudy = fieldStudyRepository.save(TestData.fieldStudy1.copy(department = savedDepartment))
        val savedModule = moduleRepository.save(TestData.module1.copy(fieldStudy = savedFieldStudy))
        return sectionRepository.save(TestData.section1.copy(module = savedModule))
    }
}
