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
import pt.isel.odin.http.controllers.tech.models.GetAllTechsOutputModel
import pt.isel.odin.http.controllers.tech.models.GetTechOutputModel
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.SaveTechOutputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechInputModel
import pt.isel.odin.model.Tech
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(Config::class)
class TechControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testUtils: ControllerTestUtils

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        testUtils.cleanDatabase()
        client = testUtils.setupClient(port, Uris.Techs.RESOURCE)
    }

    @Test
    fun `Get tech by ID`() {
        // given: a tech
        val tech = testUtils.createTech()

        // when: getting the tech by its ID
        val result = client.get()
            .uri("/${tech.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetTechOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: tech matches the expected tech
        assertEquals(tech.summary, result!!.summary)
    }

    @Test
    fun `Get tech by non-existent ID`() {
        // when: getting a non-existent tech by its ID
        // then: a not found error is returned
        client.get().uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Tech Not Found")
    }

    @Test
    fun `Get all techs`() {
        // given: two techs
        val tech1 = testUtils.createTech()
        val tech2 = testUtils.techRepository.save(
            Tech(
                teacher = tech1.teacher,
                section = tech1.section,
                started = LocalDateTime.now(),
                ended = LocalDateTime.now(),
                summary = "Tech 2",
                missTech = mutableListOf()
            )
        )

        // when: getting all techs
        val result = client.get()
            .exchange()
            .expectStatus().isOk
            .expectBody(GetAllTechsOutputModel::class.java)
            .returnResult()
            .responseBody

        val expectedTechs = listOf(
            GetTechOutputModel(tech1).copy(started = result!!.techs[0].started, ended = result.techs[0].ended),
            GetTechOutputModel(tech2).copy(started = result.techs[1].started, ended = result.techs[1].ended)
        )

        // then: the techs match the expected techs
        assertEquals(2, result.techs.size)
        assertEquals(expectedTechs, result.techs)
    }

    @Test
    fun `Save tech`() {
        // given: a tech input model
        val section = testUtils.createSection()
        val teacher = testUtils.createUser()
        val input = SaveTechInputModel(
            teacher = teacher.id!!,
            section = section.id!!,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Tech Summary",
            missTech = emptyList()
        )

        // when: saving the tech
        val techId = client.post()
            .uri(Uris.Techs.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
            .expectBody(SaveTechOutputModel::class.java)
            .returnResult()
            .responseBody

        // and: getting the tech by its ID
        val result = client.get()
            .uri("/${techId!!.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetTechOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the tech matches the expected tech
        assertEquals(input.summary, result!!.summary)
    }

    @Test
    fun `Save tech with invalid teacher`() {
        // when: saving a tech with an invalid teacher
        val section = testUtils.createSection()
        val input = SaveTechInputModel(
            teacher = 999,
            section = section.id!!,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Invalid Teacher",
            missTech = emptyList()
        )

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Techs.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("User Not Found")
    }

    @Test
    fun `Save tech with invalid section`() {
        // when: saving a tech with an invalid section
        val teacher = testUtils.createUser()
        val input = SaveTechInputModel(
            teacher = teacher.id!!,
            section = 999,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Invalid Section",
            missTech = emptyList()
        )

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Techs.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }

    @Test
    fun `Update tech`() {
        // given: a tech
        val tech = testUtils.createTech()
        val input = UpdateTechInputModel(
            id = tech.id!!,
            teacher = tech.teacher.id!!,
            section = tech.section.id!!,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Updated Summary",
            missTech = emptyList()
        )

        // when: updating the tech
        val result = client.put()
            .uri(Uris.Techs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetTechOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the tech matches the expected tech
        assertEquals(input.summary, result!!.summary)
    }

    @Test
    fun `Update non-existent tech`() {
        // when: updating a non-existent tech
        val section = testUtils.createSection()
        val teacher = testUtils.createUser()
        val input = UpdateTechInputModel(
            id = 999,
            teacher = teacher.id!!,
            section = section.id!!,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Non-Existent Tech",
            missTech = emptyList()
        )

        // then: a not found error is returned
        client.put()
            .uri(Uris.Techs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Tech Not Found")
    }

    @Test
    fun `Update tech with invalid teacher`() {
        // when: updating a tech with an invalid teacher
        val section = testUtils.createSection()
        val input = UpdateTechInputModel(
            id = 1,
            teacher = 999,
            section = section.id!!,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Invalid Teacher",
            missTech = emptyList()
        )

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Techs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("User Not Found")
    }

    @Test
    fun `Update tech with invalid section`() {
        // when: updating a tech with an invalid section
        val teacher = testUtils.createUser()
        val input = UpdateTechInputModel(
            id = 1,
            teacher = teacher.id!!,
            section = 999,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().toString(),
            summary = "Invalid Section",
            missTech = emptyList()
        )

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Techs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }

    @Test
    fun `Delete tech`() {
        // given: a tech
        val tech = testUtils.createTech()

        // when: deleting the tech
        val result = client.delete()
            .uri("/${tech.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetTechOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the tech matches the expected tech
        assertEquals(tech.summary, result!!.summary)
    }

    @Test
    fun `Delete tech - not found`() {
        // when: deleting a non-existent tech
        // then: a not found error is returned
        client.delete()
            .uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Tech Not Found")
    }
}
