package pt.isel.odin.service

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import pt.isel.odin.http.controllers.voc.models.SaveVocInputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.model.Role
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.repository.VocRepository
import pt.isel.odin.service.voc.VocService
import pt.isel.odin.service.voc.error.DeleteVocError
import pt.isel.odin.service.voc.error.GetVocError
import pt.isel.odin.service.voc.error.SaveUpdateVocError
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class VocServiceTest {

    @Autowired
    private lateinit var vocService: VocService

    @Autowired
    private lateinit var vocRepository: VocRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var moduleRepository: ModuleRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    @Autowired
    private lateinit var fieldStudyRepository: FieldStudyRepository

    @Test
    fun `Get voc by ID`() {
        // given: a saved Voc instance
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val voc = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = true,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        vocRepository.save(voc)

        // when: retrieving the voc by ID
        val result = vocService.getById(voc.id!!)

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(voc, (result as Success).value)
    }

    @Test
    fun `Get voc by non-existent ID`() {
        // when: retrieving the voc by non-existent ID
        val result = vocService.getById(1)

        // then: validate the retrieval operation
        assertTrue(result is Failure)
        assertEquals(GetVocError.NotFoundVoc, (result as Failure).value)
    }

    @Test
    fun `Get all vocs`() {
        // given: multiple saved Voc instances
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val voc1 = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = true,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        val voc2 = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = false,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        vocRepository.save(voc1)
        vocRepository.save(voc2)

        // when: retrieving all vocs
        val result = vocService.getAll()

        // then: validate the retrieval operation
        assertTrue(result is Success)
        assertEquals(listOf(voc1, voc2), (result as Success).value)
    }

    @Test
    fun `Save voc`() {
        // given: a valid SaveVocInputModel
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val saveVocInputModel = SaveVocInputModel(
            user = user.id!!,
            module = module.id!!,
            description = "Something",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )

        // when: saving the voc
        val result = vocService.save(saveVocInputModel, user.email)

        // then: validate the save operation
        assertTrue(result is Success)
        val voc = Voc(
            id = (result as Success).value.id!!,
            user = user,
            module = module,
            approved = saveVocInputModel.approved,
            started = LocalDateTime.parse(saveVocInputModel.started),
            description = "Something",
            ended = LocalDateTime.parse(saveVocInputModel.ended)
        )
        assertEquals(voc, result.value)
    }

    @Test
    fun `Save voc with non-existent user`() {
        // given: a SaveVocInputModel with a non-existent user
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val saveVocInputModel = SaveVocInputModel(
            user = 9999,
            module = module.id!!,
            description = "Something",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )

        // when: saving the voc
        val result = vocService.save(saveVocInputModel, "nonexistent@example.com")

        // then: validate the failure due to non-existent user
        assertTrue(result is Failure)
        assertEquals(SaveUpdateVocError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Save voc with non-existent module`() {
        // given: a SaveVocInputModel with a non-existent module
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val saveVocInputModel = SaveVocInputModel(
            user = user.id!!,
            module = 9999,
            description = "Something",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )

        // when: saving the voc
        val result = vocService.save(saveVocInputModel, user.email)

        // then: validate the failure due to non-existent module
        assertTrue(result is Failure)
        assertEquals(SaveUpdateVocError.NotFoundModule, (result as Failure).value)
    }

    @Test
    fun `Update voc`() {
        // given: a valid UpdateVocInputModel and existing voc
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val existingVoc = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = true,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        val voc = vocRepository.save(existingVoc)
        val updateVocInputModel = UpdateVocInputModel(
            id = voc.id!!,
            user = user.id!!,
            module = module.id!!,
            description = "Something",
            approved = false,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )
        val updatedVoc = existingVoc.copy(
            id = voc.id!!,
            user = user,
            module = module,
            approved = updateVocInputModel.approved,
            started = LocalDateTime.parse(updateVocInputModel.started),
            ended = LocalDateTime.parse(updateVocInputModel.ended)
        )

        // when: updating the voc
        val result = vocService.update(updateVocInputModel, user.email)

        // then: validate the update operation
        assertTrue(result is Success)
        assertEquals(updatedVoc, (result as Success).value)
    }

    @Test
    fun `Update non-existent voc`() {
        // given: an UpdateVocInputModel for a non-existent voc
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val updateVocInputModel = UpdateVocInputModel(
            id = 1,
            user = user.id!!,
            module = module.id!!,
            description = "Something",
            approved = false,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )

        // when: updating the non-existent voc
        val result = vocService.update(updateVocInputModel, user.email)

        // then: validate the failure due to non-existent voc
        assertTrue(result is Failure)
        assertEquals(SaveUpdateVocError.NotFoundVoc, (result as Failure).value)
    }

    @Test
    fun `Update voc with non-existent user`() {
        // given: a valid UpdateVocInputModel with a non-existent user
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val existingVoc = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = true,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        vocRepository.save(existingVoc)
        val updateVocInputModel = UpdateVocInputModel(
            id = existingVoc.id!!,
            user = 9999,
            module = module.id!!,
            description = "Something",
            approved = false,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )

        // when: updating the voc
        val result = vocService.update(updateVocInputModel, user.email)

        // then: validate the failure due to non-existent user
        assertTrue(result is Failure)
        assertEquals(SaveUpdateVocError.NotFoundUser, (result as Failure).value)
    }

    @Test
    fun `Update voc with non-existent module`() {
        // given: a valid UpdateVocInputModel with a non-existent module
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val existingVoc = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = true,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        vocRepository.save(existingVoc)
        val updateVocInputModel = UpdateVocInputModel(
            id = existingVoc.id!!,
            user = user.id!!,
            module = 9999,
            description = "Something",
            approved = false,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusMonths(1).toString()
        )

        // when: updating the voc
        val result = vocService.update(updateVocInputModel, user.email)

        // then: validate the failure due to non-existent module
        assertTrue(result is Failure)
        assertEquals(SaveUpdateVocError.NotFoundModule, (result as Failure).value)
    }

    @Test
    fun `Delete voc`() {
        // given: an existing voc ID
        val role = Role(name = "Student")
        roleRepository.save(role)
        val user = User(email = "student@example.com", username = "student", role = role)
        userRepository.save(user)
        val department = Department(name = "Science")
        departmentRepository.save(department)
        val fieldStudy = FieldStudy(name = "Physics", department = department)
        fieldStudyRepository.save(fieldStudy)
        val module = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy, tier = 1)
        moduleRepository.save(module)
        val voc = Voc(
            user = user,
            module = module,
            description = "Something",
            approved = true,
            started = LocalDateTime.now(),
            ended = LocalDateTime.now().plusMonths(1)
        )
        vocRepository.save(voc)

        // when: deleting the voc
        val result = vocService.delete(voc.id!!)

        // then: validate the delete operation
        assertTrue(result is Success)
        assertEquals(voc, (result as Success).value)

        // and: verify the delete operation
        val deletedVoc = vocRepository.findById(voc.id!!)
        assertTrue(deletedVoc.isEmpty)
    }

    @Test
    fun `Delete non-existent voc`() {
        // when: deleting the non-existent voc
        val result = vocService.delete(1)

        // then: validate the failure due to non-existent voc
        assertTrue(result is Failure)
        assertEquals(DeleteVocError.NotFoundVoc, (result as Failure).value)
    }
}
