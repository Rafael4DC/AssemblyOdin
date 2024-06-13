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
import pt.isel.odin.http.controllers.fieldstudy.models.*
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig::class)
class FieldStudyControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var fieldStudyRepository: FieldStudyRepository

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    private val baseUrl get() = "http://localhost:$port/api/fieldstudy"

    private val client by lazy {
        WebTestClient.bindToServer().baseUrl(baseUrl).build()
    }

    @BeforeEach
    fun setup() {
        fieldStudyRepository.deleteAll()
    }

    @Test
    fun `Get field study by ID`() {
        // given: a field study
        val department = departmentRepository.save(Department(name = "Department of Science"))
        val fieldStudy = fieldStudyRepository.save(FieldStudy(name = "Computer Science", department = department))

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
        val department = departmentRepository.save(Department(name = "Department of Humanities"))
        val fieldStudy1 = fieldStudyRepository.save(FieldStudy(name = "History", department = department))
        val fieldStudy2 = fieldStudyRepository.save(FieldStudy(name = "Philosophy", department = department))

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
        val department = departmentRepository.save(Department(name = "Department of Arts"))
        val input = SaveFieldStudyInputModel(name = "Music", department = department.id!!)

        // when: saving the field study
        val fieldStudyId = client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
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
        val department = departmentRepository.save(Department(name = "Department of Social Sciences"))
        fieldStudyRepository.save(FieldStudy(name = "Sociology", department = department))
        val input = SaveFieldStudyInputModel(name = "Sociology", department = department.id!!)

        // when: saving the field study
        // then: a conflict error is returned
        client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Already Exists")
    }

    @Test
    fun `Save field study with invalid name`() {
        // when: saving a field study with an invalid name
        val department = departmentRepository.save(Department(name = "Department of Invalid Names"))
        val input = SaveFieldStudyInputModel(name = "", department = department.id!!)

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
    fun `Save field study with invalid department`() {
        // when: saving a field study with an invalid department
        val input = SaveFieldStudyInputModel(name = "Invalid Field Study", department = 999)

        // then: a bad request error is returned
        client.post()
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Update field study`() {
        // given: a field study
        val department = departmentRepository.save(Department(name = "Department of Medicine"))
        val fieldStudy = fieldStudyRepository.save(FieldStudy(name = "Neurology", department = department))
        val input = UpdateFieldStudyInputModel(id = fieldStudy.id!!, name = "Cardiology", department = department.id!!)

        // when: updating the field study
        val result = client.put()
            .uri("/update")
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
        val department = departmentRepository.save(Department(name = "Department of Non-Existent Studies"))
        val input = UpdateFieldStudyInputModel(id = 999, name = "Non-Existent Field", department = department.id!!)

        // then: a not found error is returned
        client.put()
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")
    }

    @Test
    fun `Update field study with invalid name`() {
        // when: updating a field study with an invalid name
        val department = departmentRepository.save(Department(name = "Department of Valid Names"))
        val input = UpdateFieldStudyInputModel(id = 1, name = "", department = department.id!!)

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
    fun `Update field study with invalid department`() {
        // when: updating a field study with an invalid department
        val input = UpdateFieldStudyInputModel(id = 1, name = "Valid Name", department = 999)

        // then: a bad request error is returned
        client.put()
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Delete field study`() {
        // given: a field study
        val department = departmentRepository.save(Department(name = "Department to be Deleted"))
        val fieldStudy = fieldStudyRepository.save(FieldStudy(name = "Field to be Deleted", department = department))

        // when: deleting the field study
        val result = client.delete()
            .uri("/${fieldStudy.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetFieldStudyOutputModel::class.java)
            .returnResult()
            .responseBody

        client.get().uri("/${fieldStudy.id}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Field Study Not Found")

        // then: the field study matches the expected field study
        assertEquals(fieldStudy.name, result!!.name)
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
