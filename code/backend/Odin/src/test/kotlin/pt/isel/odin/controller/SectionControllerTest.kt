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
import pt.isel.odin.controller.utils.Config
import pt.isel.odin.controller.utils.ControllerTestUtils
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.section.models.GetAllSectionsOutputModel
import pt.isel.odin.http.controllers.section.models.GetSectionOutputModel
import pt.isel.odin.http.controllers.section.models.SaveSectionInputModel
import pt.isel.odin.http.controllers.section.models.SaveSectionOutputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionInputModel

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(Config::class)
class SectionControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testUtils: ControllerTestUtils

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        testUtils.cleanDatabase()
        client = testUtils.setupClient(port, Uris.Sections.RESOURCE)
    }

    @Test
    fun `Get section by ID`() {
        // given: a section
        val section = testUtils.createSection()

        // when: getting the section by its ID
        val result = client.get()
            .uri("/${section.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetSectionOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: section matches the expected section
        assertEquals(section.name, result!!.name)
    }

    @Test
    fun `Get section by non-existent ID`() {
        // when: getting a non-existent section by its ID
        // then: a not found error is returned
        client.get().uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }

    @Test
    fun `Get all sections`() {
        // given: two sections
        val section1 = testUtils.createSection()
        val section2 = testUtils.createSection()

        val expectedSections = listOf(
            GetSectionOutputModel(section1),
            GetSectionOutputModel(section2)
        )

        // when: getting all sections
        val result = client.get()
            .exchange()
            .expectStatus().isOk
            .expectBody(GetAllSectionsOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the sections match the expected sections
        assertEquals(2, result!!.sections.size)
        assertEquals(expectedSections, result.sections)
    }

    @Test
    fun `Save section`() {
        // given: a section input model
        val module = testUtils.createModule()
        val input = SaveSectionInputModel(
            name = "Section D",
            module = module.id!!,
            students = emptyList()
        )

        // when: saving the section
        val sectionId = client.post()
            .uri(Uris.Sections.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
            .expectBody(SaveSectionOutputModel::class.java)
            .returnResult()
            .responseBody

        // and: getting the section by its ID
        val result = client.get()
            .uri("/${sectionId!!.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetSectionOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the section matches the expected section
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Save section with duplicate name`() {
        // given: a section with the same name
        val section = testUtils.createSection()
        val input = SaveSectionInputModel(
            name = section.name,
            module = section.module!!.id!!,
            students = emptyList()
        )

        // when: saving the section
        // then: a conflict error is returned
        client.post()
            .uri(Uris.Sections.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Already Exists")
    }

    @Test
    fun `Save section with invalid name`() {
        // when: saving a section with an invalid name
        val module = testUtils.createModule()
        val input =
            SaveSectionInputModel(name = "", module = module.id!!, students = emptyList())

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Sections.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Save section with invalid module`() {
        // when: saving a section with an invalid module
        val input =
            SaveSectionInputModel(name = "Section H", module = 999, students = emptyList())

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Sections.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")
    }

    @Test
    fun `Update section`() {
        // given: a section
        val section = testUtils.createSection()

        val input = UpdateSectionInputModel(
            id = section.id!!,
            name = "Updated Section I",
            module = section.module!!.id!!,
            students = emptyList()
        )

        // when: updating the section
        val result = client.put()
            .uri(Uris.Sections.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetSectionOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the section matches the expected section
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Update non-existent section`() {
        // when: updating a non-existent section
        val module = testUtils.createModule()
        val input = UpdateSectionInputModel(
            id = 999,
            name = "Non-Existent Section",
            module = module.id!!,
            students = emptyList()
        )

        // then: a not found error is returned
        client.put()
            .uri(Uris.Sections.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }

    @Test
    fun `Update section with invalid name`() {
        // when: updating a section with an invalid name
        val module = testUtils.createModule()
        val input = UpdateSectionInputModel(
            id = 1,
            name = "",
            module = module.id!!,
            students = emptyList()
        )

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Sections.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Update section with invalid module`() {
        // when: updating a section with an invalid module
        val input = UpdateSectionInputModel(
            id = 1,
            name = "Valid Name",
            module = 999,
            students = emptyList()
        )

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Sections.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Module Not Found")
    }

    @Test
    fun `Delete section`() {
        // given: a section
        val section = testUtils.createSection()

        // when: deleting the section
        val result = client.delete()
            .uri("/${section.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetSectionOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the section matches the expected section
        assertEquals(section.name, result!!.name)
    }

    @Test
    fun `Delete section - not found`() {
        // when: deleting a non-existent section
        // then: a not found error is returned
        client.delete()
            .uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }
}
