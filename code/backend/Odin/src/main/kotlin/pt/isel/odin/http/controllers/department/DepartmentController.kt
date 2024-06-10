package pt.isel.odin.http.controllers.department

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.http.controllers.department.models.GetDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.SaveDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.SaveDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.getAllDepartmentsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.service.department.DepartmentService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the departments.
 */
@RestController
@RequestMapping("/api/department")
class DepartmentController(private val departmentService: DepartmentService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val user = departmentService.getById(id)) {
            is Success -> responde(GetDepartmentOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val users = departmentService.getAll()) {
            is Success -> responde(getAllDepartmentsOutputModel(users.value))
            is Failure -> Problem.responseForError(users.value)
        }

    @PostMapping("/save")
    fun save(@RequestBody saveDepartInputModel: SaveDepartmentInputModel): ResponseEntity<*> =
        when (val user = departmentService.save(saveDepartInputModel)) {
            is Success -> responde(SaveDepartmentOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    @PutMapping("/update")
    fun update(@RequestBody updateDepartInputModel: UpdateDepartmentInputModel): ResponseEntity<*> =
        when (val user = departmentService.update(updateDepartInputModel)) {
            is Success -> responde(UpdateDepartmentOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val user = departmentService.delete(id)) {
            is Success -> responde(GetDepartmentOutputModel(user.value))
            is Failure -> Problem.responseForError(user.value)
        }
}
