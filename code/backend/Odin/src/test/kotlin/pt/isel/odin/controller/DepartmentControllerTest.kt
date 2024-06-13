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
import pt.isel.odin.http.controllers.department.models.*
import pt.isel.odin.model.Department
import pt.isel.odin.repository.DepartmentRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig::class)
class DepartmentControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var departmentRepository: DepartmentRepository

    private val baseUrl get() = "http://localhost:$port/api/department"

    private val client by lazy {
        WebTestClient.bindToServer().baseUrl(baseUrl).build()
    }

    @BeforeEach
    fun setup() {
        departmentRepository.deleteAll()
    }

    @Test
    fun `Get department by ID`() {
        // given: a department
        val department = departmentRepository.save(Department(name = "Department of Computer Science"))

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
        val department1 = departmentRepository.save(Department(name = "Department of Math"))
        val department2 = departmentRepository.save(Department(name = "Department of Physics"))

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
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isOk
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
        departmentRepository.save(Department(name = "Existing Department"))
        val input = SaveDepartmentInputModel(name = "Existing Department")

        // when: saving the department
        // then: a conflict error is returned
        client.post()
            .uri("/save")
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
            .uri("/save")
            .bodyValue(input)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Update department`() {
        // given: a department
        val department = departmentRepository.save(Department(name = "Old Department"))
        val input = UpdateDepartmentInputModel(id = department.id!!, name = "Updated Department")

        // when: updating the department
        val result = client.put()
            .uri("/update")
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
            .uri("/update")
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
            .uri("/update")
            .bodyValue(input)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Name Incorrect")
    }

    @Test
    fun `Delete department`() {
        // given: a department
        val department = departmentRepository.save(Department(name = "Department to be Deleted"))

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
}
