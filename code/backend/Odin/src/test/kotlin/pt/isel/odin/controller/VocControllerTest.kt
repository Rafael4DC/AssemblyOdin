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
import pt.isel.odin.http.controllers.voc.models.GetAllVocsOutputModel
import pt.isel.odin.http.controllers.voc.models.GetVocOutputModel
import pt.isel.odin.http.controllers.voc.models.SaveVocInputModel
import pt.isel.odin.http.controllers.voc.models.SaveVocOutputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.model.Voc
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(Config::class)
class VocControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testUtils: ControllerTestUtils

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        testUtils.cleanDatabase()
        client = testUtils.setupClient(port, Uris.Vocs.RESOURCE)
    }

    @Test
    fun `Get voc by ID`() {
        // given: a voc
        val voc = testUtils.createVoc()

        // when: getting the voc by its ID
        val result = client.get()
            .uri("/${voc.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetVocOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: voc matches the expected voc
        assertEquals(voc.approved, result!!.approved)
    }

    @Test
    fun `Get voc by non-existent ID`() {
        // when: getting a non-existent voc by its ID
        // then: a not found error is returned
        client.get().uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Voc Not Found")
    }

    @Test
    fun `Get all vocs`() {
        // given: two vocs
        val voc1 = testUtils.createVoc()
        val voc2 = testUtils.vocRepository.save(
            Voc(
                user = voc1.user,
                section = voc1.section,
                description = "Description",
                approved = true,
                started = LocalDateTime.now(),
                ended = LocalDateTime.now().plusHours(1)
            )
        )

        // when: getting all vocs
        val result = client.get()
            .exchange()
            .expectStatus().isOk
            .expectBody(GetAllVocsOutputModel::class.java)
            .returnResult()
            .responseBody

        val expectedVocs = listOf(
            GetVocOutputModel(voc1).copy(ended = result!!.vocs[0].ended, started = result.vocs[0].started),
            GetVocOutputModel(voc2).copy(ended = result.vocs[1].ended, started = result.vocs[1].started)
        )

        // then: the vocs match the expected vocs
        assertEquals(2, result.vocs.size)
        assertEquals(expectedVocs, result.vocs)
    }

    @Test
    fun `Save voc`() {
        // given: a voc input model
        val section = testUtils.createSection()
        val user = testUtils.createUser()
        val input = SaveVocInputModel(
            user = user.id!!,
            section = section.id!!,
            approved = true,
            description = "Description",
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusHours(1).toString()
        )

        // when: saving the voc
        val vocId = client.post()
            .uri(Uris.Vocs.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
            .expectBody(SaveVocOutputModel::class.java)
            .returnResult()
            .responseBody

        // and: getting the voc by its ID
        val result = client.get()
            .uri("/${vocId!!.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetVocOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the voc matches the expected voc
        assertEquals(input.approved, result!!.approved)
    }

    @Test
    fun `Save voc with invalid user`() {
        // when: saving a voc with an invalid user
        val section = testUtils.createSection()
        val input = SaveVocInputModel(
            user = 999,
            section = section.id!!,
            description = "Description",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusHours(1).toString()
        )

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Vocs.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("User Not Found")
    }

    @Test
    fun `Save voc with invalid section`() {
        // when: saving a voc with an invalid section
        val user = testUtils.createUser()
        val input = SaveVocInputModel(
            user = user.id!!,
            section = 999,
            description = "Description",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusHours(1).toString()
        )

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Vocs.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }

    @Test
    fun `Update voc`() {
        // given: a voc
        val voc = testUtils.createVoc()
        val input = UpdateVocInputModel(
            id = voc.id!!,
            user = voc.user.id!!,
            section = voc.section.id!!,
            description = voc.description,
            approved = !voc.approved,
            started = voc.started.toString(),
            ended = voc.ended.toString()
        )

        // when: updating the voc
        val result = client.put()
            .uri(Uris.Vocs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetVocOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the voc matches the expected voc
        assertEquals(input.approved, result!!.approved)
    }

    @Test
    fun `Update non-existent voc`() {
        // when: updating a non-existent voc
        val section = testUtils.createSection()
        val user = testUtils.createUser()
        val input = UpdateVocInputModel(
            id = 999,
            user = user.id!!,
            section = section.id!!,
            description = "Description",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusHours(1).toString()
        )

        // then: a not found error is returned
        client.put()
            .uri(Uris.Vocs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Voc Not Found")
    }

    @Test
    fun `Update voc with invalid user`() {
        // when: updating a voc with an invalid user
        val section = testUtils.createSection()
        val input = UpdateVocInputModel(
            id = 1,
            user = 999,
            section = section.id!!,
            description = "Description",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusHours(1).toString()
        )

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Vocs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("User Not Found")
    }

    @Test
    fun `Update voc with invalid section`() {
        // when: updating a voc with an invalid section
        val user = testUtils.createUser()
        val input = UpdateVocInputModel(
            id = 1,
            user = user.id!!,
            section = 999,
            description = "Description",
            approved = true,
            started = LocalDateTime.now().toString(),
            ended = LocalDateTime.now().plusHours(1).toString()
        )

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Vocs.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Section Not Found")
    }

    @Test
    fun `Delete voc`() {
        // given: a voc
        val section = testUtils.createSection()
        val user = testUtils.createUser()
        val voc = testUtils.vocRepository.save(
            Voc(
                user = user,
                section = section,
                description = "Description",
                approved = true,
                started = LocalDateTime.now(),
                ended = LocalDateTime.now().plusHours(1)
            )
        )

        // when: deleting the voc
        val result = client.delete()
            .uri("/${voc.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetVocOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the voc matches the expected voc
        assertEquals(voc.approved, result!!.approved)
    }

    @Test
    fun `Delete voc - not found`() {
        // when: deleting a non-existent voc
        // then: a not found error is returned
        client.delete()
            .uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Voc Not Found")
    }
}
