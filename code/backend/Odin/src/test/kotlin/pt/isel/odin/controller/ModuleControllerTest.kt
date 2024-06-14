package pt.isel.odin.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.odin.controller.utils.TestSecurityConfig
import pt.isel.odin.http.controllers.module.models.*
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig::class)
class ModuleControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var moduleRepository: ModuleRepository

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    private val baseUrl get() = "http://localhost:$port/api/modules"

    private val client by lazy {
        WebTestClient.bindToServer().baseUrl(baseUrl).build()
    }

    @BeforeEach
    fun setup() {
        moduleRepository.deleteAll()
    }

    @Test
    fun `Get module by ID`() {
        // given: a module
        val module = getModule()

        // when: getting the module by its ID
        val result = client.get()
            .uri("/${module.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: module matches the expected module
        assertEquals(module.name, result!!.name)
    }

    @Test
    fun `Get module by non-existent ID`() {
        // when: getting a non-existent module by its ID
        // then: a not found error is returned
        client.get().uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")
    }

    @Test
    fun `Get all modules`() {
        // given: two modules
        val module1 = getModule()
        val module2 = getModule("Thermodynamics")

        val expectedModules = listOf(
            GetModuleOutputModel(module1),
            GetModuleOutputModel(module2)
        )

        // when: getting all modules
        val result = client.get()
            .exchange()
            .expectStatus().isOk
            .expectBody(GetAllModulesOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the modules match the expected modules
        assertEquals(2, result!!.modules.size)
        assertEquals(expectedModules, result.modules)
    }

    @Test
    fun `Save module`() {
        // given: a module input model
        val module = getModule()
        val input = SaveModuleInputModel(name = "Painting 101", fieldStudy = module.fieldStudy.id!!, tier = 1)

        // when: saving the module
        val moduleId = client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(SaveModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        // and: getting the module by its ID
        val result = client.get()
            .uri("/${moduleId!!.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the module matches the expected module
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Save module with duplicate name`() {
        // given: a module with the same name
        val module = getModule()
        val input = SaveModuleInputModel(name = module.name, fieldStudy = module.fieldStudy.id!!, tier = module.tier)

        // when: saving the module
        // then: a conflict error is returned
        client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody()
            .jsonPath("$.title").isEqualTo("A Module Already Exists With That Name")
    }

    @Test
    fun `Save module with invalid name`() {
        // when: saving a module with an invalid name
        val module = getModule()
        val input = SaveModuleInputModel(name = "", fieldStudy = module.fieldStudy.id!!, tier = 1)

        // then: a bad request error is returned
        client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Save module with invalid field study`() {
        // when: saving a module with an invalid field study
        val input = SaveModuleInputModel(name = "Invalid Field Study", fieldStudy = 999, tier = 1)

        // then: a bad request error is returned
        client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    fun `Update module`() {
        // given: a module
        val module = getModule()
        val input = UpdateModuleInputModel(id = module.id!!, name = "Advanced Anatomy", fieldStudy = module.fieldStudy.id!!, tier = 2)

        // when: updating the module
        val result = client.put()
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the module matches the expected module
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Update non-existent module`() {
        // when: updating a non-existent module
        val module = getModule()
        val input = UpdateModuleInputModel(id = 999, name = "Non-Existent Module", fieldStudy = module.fieldStudy.id!!, tier = 1)

        // then: a not found error is returned
        client.put()
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")
    }

    @Test
    fun `Update module with invalid name`() {
        // when: updating a module with an invalid name
        val module = getModule()
        val input = UpdateModuleInputModel(id = 1, name = "", fieldStudy = module.fieldStudy.id!!, tier = 1)

        // then: a bad request error is returned
        client.put()
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Update module with invalid field study`() {
        // when: updating a module with an invalid field study
        val input = UpdateModuleInputModel(id = 1, name = "Valid Name", fieldStudy = 999, tier = 1)

        // then: a bad request error is returned
        client.put()
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    fun `Delete module`() {
        // given: a module
        val module = getModule()

        // when: deleting the module
        val result = client.delete()
            .uri("/${module.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the module matches the expected module
        assertEquals(module.name, result!!.name)
    }

    @Test
    fun `Delete module - not found`() {
        // when: deleting a non-existent module
        // then: a not found error is returned
        client.delete()
            .uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")
    }

    fun getModule(name: String? = null): Module {
        val department = departmentRepository.save(Department(name = "Department of Humanities"))
        val fieldStudy = fieldStudyRepository.save(FieldStudy(name = "History", department = department))
        return moduleRepository.save(
            Module(name = name ?: "Programming 101", fieldStudy = fieldStudy, tier = 1)
        )
    }
}
