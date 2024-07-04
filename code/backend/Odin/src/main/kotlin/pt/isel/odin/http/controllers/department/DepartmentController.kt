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
import pt.isel.odin.http.controllers.Uris
import pt.isel.odin.http.controllers.department.models.GetDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.SaveDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.SaveDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentOutputModel
import pt.isel.odin.http.controllers.department.models.getAllDepartmentsOutputModel
import pt.isel.odin.http.utils.Problem
import pt.isel.odin.http.utils.responde
import pt.isel.odin.model.Department
import pt.isel.odin.service.department.DepartmentService
import pt.isel.odin.utils.Failure
import pt.isel.odin.utils.Success

/**
 * Represents the controller that contains the endpoints related to the departments.
 */
@RestController
@RequestMapping(Uris.Departments.RESOURCE)
class DepartmentController(private val departmentService: DepartmentService) {

    /**
     * Gets a department by its id.
     *
     * @param id the department id.
     *
     * @return The [Department].
     */
    @GetMapping(Uris.Departments.GET_BY_ID)
    fun getById(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = departmentService.getById(id)) {
            is Success -> responde(GetDepartmentOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Gets all departments.
     *
     * @return All [Department].
     */
    @GetMapping
    fun getAll(): ResponseEntity<*> =
        when (val result = departmentService.getAll()) {
            is Success -> responde(getAllDepartmentsOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Saves a new department.
     *
     * @param saveDepartInputModel the department to save.
     *
     * @return The saved [Department] id.
     */
    @PostMapping(Uris.Departments.SAVE)
    fun save(@RequestBody saveDepartInputModel: SaveDepartmentInputModel): ResponseEntity<*> =
        when (val result = departmentService.save(saveDepartInputModel)) {
            is Success -> responde(
                SaveDepartmentOutputModel(result.value),
                201,
                Uris.Departments.byId(result.value.id)
            )

            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Updates a department.
     *
     * @param updateDepartInputModel the department to update.
     *
     * @return The updated [Department].
     */
    @PutMapping(Uris.Departments.UPDATE)
    fun update(@RequestBody updateDepartInputModel: UpdateDepartmentInputModel): ResponseEntity<*> =
        when (val result = departmentService.update(updateDepartInputModel)) {
            is Success -> responde(UpdateDepartmentOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }

    /**
     * Deletes a department.
     *
     * @param id the department id.
     *
     * @return The deleted [Department].
     */
    @DeleteMapping(Uris.Departments.DELETE)
    fun delete(@PathVariable id: Long): ResponseEntity<*> =
        when (val result = departmentService.delete(id)) {
            is Success -> responde(GetDepartmentOutputModel(result.value))
            is Failure -> Problem.responseForError(result.value)
        }
}
