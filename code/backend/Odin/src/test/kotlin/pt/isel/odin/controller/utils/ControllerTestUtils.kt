package pt.isel.odin.controller.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.model.Role
import pt.isel.odin.model.Section
import pt.isel.odin.model.Tech
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.RoleRepository
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.repository.VocRepository
import java.time.LocalDateTime

@Component
class ControllerTestUtils {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var moduleRepository: ModuleRepository

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var techRepository: TechRepository

    @Autowired
    lateinit var vocRepository: VocRepository

    fun cleanDatabase() {
        techRepository.deleteAll()
        vocRepository.deleteAll()
        userRepository.deleteAll()
        roleRepository.deleteAll()
        sectionRepository.deleteAll()
        moduleRepository.deleteAll()
        fieldStudyRepository.deleteAll()
        departmentRepository.deleteAll()
    }

    fun setupClient(port: Int, path: String): WebTestClient {
        val baseUrl = "http://localhost:$port$path"
        return WebTestClient.bindToServer().baseUrl(baseUrl).build()
    }

    fun createRole(name: String? = null): Role =
        roleRepository.save(Role(name = name ?: "ROLE_USER"))

    fun createUser(email: String? = null, givenRole: Role? = null): User {
        val role = givenRole ?: createRole()
        return userRepository.save(
            User(
                email = email ?: "test@example.com",
                username = "testuser",
                role = role
            )
        )
    }

    fun createDepartment(name: String? = null): Department =
        departmentRepository.save(Department(name = name ?: "Department of Humanities"))

    fun createFieldStudy(name: String? = null): FieldStudy {
        val department = createDepartment()
        return fieldStudyRepository.save(FieldStudy(name = name ?: "History", department = department))
    }

    fun createModule(name: String? = null): Module {
        val fieldStudy = createFieldStudy()
        return moduleRepository.save(
            Module(name = name ?: "Programming 101", fieldStudy = fieldStudy, tier = 1)
        )
    }

    fun createSection(name: String? = null): Section {
        val module = createModule()
        return sectionRepository.save(
            Section(
                name = name ?: "Section A",
                summary = "Summary A",
                module = module,
                students = mutableListOf()
            )
        )
    }

    fun createTech(): Tech {
        val teacher = createUser()
        val section = createSection()
        return techRepository.save(
            Tech(
                teacher = teacher,
                section = section,
                date = LocalDateTime.now(),
                summary = "Tech Summary",
                missTech = mutableListOf()
            )
        )
    }

    fun createVoc(): Voc {
        val user = createUser()
        val section = createSection()
        return vocRepository.save(
            Voc(
                user = user,
                section = section,
                approved = true,
                description = "Description",
                started = LocalDateTime.now(),
                ended = LocalDateTime.now().plusHours(1)
            )
        )
    }
}
