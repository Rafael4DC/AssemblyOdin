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
import pt.isel.odin.http.controllers.fieldstudy.models.GetAllFieldsStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.GetFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyOutputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyInputModel

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(Config::class)
class FieldStudyControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testUtils: ControllerTestUtils

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        testUtils.cleanDatabase()
        client = testUtils.setupClient(port, Uris.FieldsStudy.RESOURCE)
    }

    @Test
    fun `Get field study by ID`() {
        // given: a field study
        val fieldStudy = testUtils.createFieldStudy()

        // when: getting the field study by its ID
        val result = client.get()
            .uri("/${fieldStudy.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetFieldStudyOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: field study matches the expected field study
        assertEquals(fieldStudy.name, result!!.name)
    }

    @Test
    fun `Get field study by non-existent ID`() {
        // when: getting a non-existent field study by its ID
        // then: a not found error is returned
        client.get().uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    fun `Get all field studies`() {
        // given: two field studies
        val fieldStudy1 = testUtils.createFieldStudy()
        val fieldStudy2 = testUtils.createFieldStudy()

        val expectedFieldStudies = listOf(
            GetFieldStudyOutputModel(fieldStudy1),
            GetFieldStudyOutputModel(fieldStudy2)
        )

        // when: getting all field studies
        val result = client.get()
            .exchange()
            .expectStatus().isOk
            .expectBody(GetAllFieldsStudyOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the field studies match the expected field studies
        assertEquals(2, result!!.fieldsStudy.size)
        assertEquals(expectedFieldStudies, result.fieldsStudy)
    }

    @Test
    fun `Save field study`() {
        // given: a field study input model
        val department = testUtils.createDepartment()
        val input = SaveFieldStudyInputModel(name = "Music", department = department.id!!)

        // when: saving the field study
        val fieldStudyId = client.post()
            .uri(Uris.FieldsStudy.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
            .expectBody(SaveFieldStudyOutputModel::class.java)
            .returnResult()
            .responseBody

        // and: getting the field study by its ID
        val result = client.get()
            .uri("/${fieldStudyId!!.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetFieldStudyOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the field study matches the expected field study
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Save field study with duplicate name`() {
        // given: a field study with the same name
        val fieldStudy = testUtils.createFieldStudy()
        val input = SaveFieldStudyInputModel(name = fieldStudy.name, department = fieldStudy.department.id!!)

        // when: saving the field study
        // then: a conflict error is returned
        client.post()
            .uri(Uris.FieldsStudy.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Already Exists")
    }

    @Test
    fun `Save field study with invalid name`() {
        // when: saving a field study with an invalid name
        val department = testUtils.createDepartment()
        val input = SaveFieldStudyInputModel(name = "", department = department.id!!)

        // then: a bad request error is returned
        client.post()
            .uri(Uris.FieldsStudy.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Save field study with invalid department`() {
        // when: saving a field study with an invalid department
        val input = SaveFieldStudyInputModel(name = "Invalid Field Study", department = 999)

        // then: a bad request error is returned
        client.post()
            .uri(Uris.FieldsStudy.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Update field study`() {
        // given: a field study
        val fieldStudy = testUtils.createFieldStudy()
        val input = UpdateFieldStudyInputModel(
            id = fieldStudy.id!!,
            name = "Cardiology",
            department = fieldStudy.department.id!!
        )

        // when: updating the field study
        val result = client.put()
            .uri(Uris.FieldsStudy.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetFieldStudyOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the field study matches the expected field study
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Update non-existent field study`() {
        // when: updating a non-existent field study
        val department = testUtils.createDepartment()
        val input = UpdateFieldStudyInputModel(id = 999, name = "Non-Existent Field", department = department.id!!)

        // then: a not found error is returned
        client.put()
            .uri(Uris.FieldsStudy.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    fun `Update field study with invalid name`() {
        // when: updating a field study with an invalid name
        val department = testUtils.createDepartment()
        val input = UpdateFieldStudyInputModel(id = 1, name = "", department = department.id!!)

        // then: a bad request error is returned
        client.put()
            .uri(Uris.FieldsStudy.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Update field study with invalid department`() {
        // when: updating a field study with an invalid department
        val input = UpdateFieldStudyInputModel(id = 1, name = "Valid Name", department = 999)

        // then: a bad request error is returned
        client.put()
            .uri(Uris.FieldsStudy.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Delete field study - not found`() {
        // when: deleting a non-existent field study
        // then: a not found error is returned
        client.delete()
            .uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }
}
