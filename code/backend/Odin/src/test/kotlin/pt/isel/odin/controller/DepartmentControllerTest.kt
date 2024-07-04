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
import pt.isel.odin.http.controllers.department.models.GetAllDepartmentsOutputModel
import pt.isel.odin.http.controllers.department.models.GetDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.SaveDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.SaveDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentInputModel
import pt.isel.odin.model.Department

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(Config::class)
class DepartmentControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testUtils: ControllerTestUtils

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        testUtils.cleanDatabase()
        client = testUtils.setupClient(port, Uris.Departments.RESOURCE)
    }

    @Test
    fun `Get department by ID`() {
        // given: a department
        val department = testUtils.createDepartment()

        // when: getting the department by its ID
        val result = client.get()
            .uri("/${department.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(Department::class.java)
            .returnResult()
            .responseBody

        // then: department matches the expected department
        assertEquals(department.name, result!!.name)
    }

    @Test
    fun `Get department by non-existent ID`() {
        // when: getting a non-existent department by its ID
        // then: a not found error is returned
        client.get().uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Get all departments`() {
        // given: two departments
        val department1 = testUtils.createDepartment()
        val department2 = testUtils.createDepartment()

        val expectedDepartments = listOf(
            GetDepartmentOutputModel(department1),
            GetDepartmentOutputModel(department2)
        )

        // when: getting all departments
        val result = client.get()
            .exchange()
            .expectStatus().isOk
            .expectBody(GetAllDepartmentsOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the departments match the expected departments
        assertEquals(2, result!!.departments.size)
        assertEquals(expectedDepartments, result.departments)
    }

    @Test
    fun `Save department`() {
        // given: a department input model
        val input = SaveDepartmentInputModel(name = "New Department")

        // when: saving the department
        val departmentId = client.post()
            .uri(Uris.Departments.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isCreated
            .expectBody(SaveDepartmentOutputModel::class.java)
            .returnResult()
            .responseBody

        // and: getting the department by its ID
        val result = client.get()
            .uri("/${departmentId!!.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(Department::class.java)
            .returnResult()
            .responseBody

        // then: the department matches the expected department
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Save department with duplicate name`() {
        // given: a department with the same name
        val department = testUtils.createDepartment()
        val input = SaveDepartmentInputModel(name = department.name)

        // when: saving the department
        // then: a conflict error is returned
        client.post()
            .uri(Uris.Departments.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Already Exists")
    }

    @Test
    fun `Save department with invalid name`() {
        // when: saving a department with an invalid name
        val input = SaveDepartmentInputModel(name = "")

        // then: a bad request error is returned
        client.post()
            .uri(Uris.Departments.SAVE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Update department`() {
        // given: a department
        val department = testUtils.createDepartment()
        val input = UpdateDepartmentInputModel(id = department.id!!, name = "Updated Department")

        // when: updating the department
        val result = client.put()
            .uri(Uris.Departments.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
            .expectBody(GetDepartmentOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the department matches the expected department
        assertEquals(input.name, result!!.name)
    }

    @Test
    fun `Update non-existent department`() {
        // when: updating a non-existent department
        val input = UpdateDepartmentInputModel(id = 999, name = "Non-Existent Department")

        // then: a not found error is returned
        client.put()
            .uri(Uris.Departments.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Update department with invalid name`() {
        // when: updating a department with an invalid name
        val input = UpdateDepartmentInputModel(id = 1, name = "")

        // then: a bad request error is returned
        client.put()
            .uri(Uris.Departments.UPDATE)
            .bodyValue(input)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Delete department`() {
        // given: a department
        val department = testUtils.createDepartment()

        // when: deleting the department
        val result = client.delete()
            .uri("/${department.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetDepartmentOutputModel::class.java)
            .returnResult()
            .responseBody

        // then: the department matches the expected department
        assertEquals(department.name, result!!.name)
    }

    @Test
    fun `Delete department - not found`() {
        // when: deleting a non-existent department
        // then: a not found error is returned
        client.delete()
            .uri("/999")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")
    }

    @Test
    fun `Delete department that has field of study`() {
        // given: a department with sections
        val fieldStudy = testUtils.createFieldStudy()
        val department = fieldStudy.department

        // when: deleting the department
        val result = client.delete()
            .uri("/${department.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetDepartmentOutputModel::class.java)
            .returnResult()
            .responseBody

        client.get().uri("/${department.id}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")

        // then: the department matches the expected department
        assertEquals(department.name, result!!.name)
    }

    @Test
    fun `Delete department that has field of study and modules`() {
        // given: a department with sections
        val module = testUtils.createModule()
        val department = module.fieldStudy.department

        // when: deleting the department
        val result = client.delete()
            .uri("/${department.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(GetDepartmentOutputModel::class.java)
            .returnResult()
            .responseBody

        client.get().uri("/${department.id}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("Department Not Found")

        // then: the department matches the expected department
        assertEquals(department.name, result!!.name)
    }
}
