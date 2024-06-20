package pt.isel.odin.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import pt.isel.odin.controller.utils.Config
import pt.isel.odin.controller.utils.ControllerTestUtils
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.module.models.GetAllModulesOutputModel
import pt.isel.odin.http.controllers.module.models.GetModuleOutputModel
import pt.isel.odin.http.controllers.module.models.SaveModuleInputModel
import pt.isel.odin.http.controllers.module.models.SaveModuleOutputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleInputModel

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(Config::class)
class ModuleControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testUtils: ControllerTestUtils

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        testUtils.cleanDatabase()
        client = testUtils.setupClient(port, Uris.Modules.RESOURCE)
    }

    @Test
    fun `Get module by ID`() {
        // given: a module
        val module = testUtils.createModule()

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
        val module1 = testUtils.createModule()
        val module2 = testUtils.createModule()

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
        val fieldStudy = testUtils.createFieldStudy()
        val input = SaveModuleInputModel(name = "Painting 101", fieldStudy = fieldStudy.id!!, tier = 1)

        // when: saving the module
        val moduleId = client.post()
            .uri(Uris.Modules.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
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
        val module = testUtils.createModule()
        val input = SaveModuleInputModel(name = module.name, fieldStudy = module.fieldStudy.id!!, tier = module.tier)

        // when: saving the module
        // then: a conflict error is returned
        client.post()
            .uri(Uris.Modules.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Already Exists")
    }

    @Test
    fun `Save module with invalid name`() {
        // when: saving a module with an invalid name
        val module = testUtils.createModule()
        val input = SaveModuleInputModel(name = "", fieldStudy = module.fieldStudy.id!!, tier = 1)

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Modules.SAVE)
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
            .uri(Uris.Modules.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    fun `Update module`() {
        // given: a module
        val module = testUtils.createModule()
        val input = UpdateModuleInputModel(
            id = module.id!!,
            name = "Advanced Anatomy",
            fieldStudy = module.fieldStudy.id!!,
            tier = 2
        )

        // when: updating the module
        val result = client.put()
            .uri(Uris.Modules.UPDATE)
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
        val module = testUtils.createModule()
        val input = UpdateModuleInputModel(
            id = 999,
            name = "Non-Existent Module",
            fieldStudy = module.fieldStudy.id!!,
            tier = 1
        )

        // then: a not found error is returned
        client.put()
            .uri(Uris.Modules.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")
    }

    @Test
    fun `Update module with invalid name`() {
        // when: updating a module with an invalid name
        val module = testUtils.createModule()
        val input = UpdateModuleInputModel(id = 1, name = "", fieldStudy = module.fieldStudy.id!!, tier = 1)

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Modules.UPDATE)
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
            .uri(Uris.Modules.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    @Disabled
    fun `Delete module`() {
        // given: a module
        val module = testUtils.createModule()

        // when: deleting the module
        val result = client.delete()
            .uri("/${module.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        client.get().uri("/${module.id}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")

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

    @Test
    @Disabled
    fun `Delete module that has section`() {
        // given: a module
        val section = testUtils.createSection()
        val module = section.module!!

        // when: deleting the module
        val result = client.delete()
            .uri("/${module.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetModuleOutputModel::class.java)
            .returnResult()
            .responseBody

        client.get().uri("/${module.id}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")

        // then: the module matches the expected module
        assertEquals(module.name, result!!.name)
    }
}
