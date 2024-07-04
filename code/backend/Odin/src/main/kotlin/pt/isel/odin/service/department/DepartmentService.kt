package pt.isel.odin.service.department

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.department.models.SaveDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentInputModel
import pt.isel.odin.model.Department
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.service.department.error.DeleteDepartmentError
import pt.isel.odin.service.department.error.GetDepartmentError
import pt.isel.odin.service.department.error.SaveUpdateDepartmentError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

/**
 * Service for Departments.
 */
@Service
class DepartmentService(private val departmentRepository: DepartmentRepository) {

    /**
     * Gets a department by its id.
     *
     * @param id the department id
     *
     * @return the [GetDepartmentResult] if found, [GetDepartmentError.NotFoundDepartment] otherwise
     */
    fun getById(id: Long): GetDepartmentResult =
        departmentRepository.findById(id)
            .map<GetDepartmentResult> { depart -> success(depart) }
            .orElse(failure(GetDepartmentError.NotFoundDepartment))

    /**
     * Gets all departments.
     *
     * @return the [GetAllDepartmentsResult] with the list of [Department]
     */
    fun getAll(): GetAllDepartmentsResult = success(departmentRepository.findAll())

    /**
     * Saves a department.
     *
     * @param saveDepartInputModel the department to save
     *
     * @return the [CreationDepartmentResult] if saved, [SaveUpdateDepartmentError] otherwise
     */
    @Transactional
    fun save(saveDepartInputModel: SaveDepartmentInputModel): CreationDepartmentResult {
        if (departmentRepository.findByName(saveDepartInputModel.name).isPresent) {
            return failure(SaveUpdateDepartmentError.AlreadyExistsDepartment)
        }

        if (saveDepartInputModel.name.isBlank()) {
            return failure(SaveUpdateDepartmentError.IncorrectNameDepartment)
        }

        return success(departmentRepository.save(saveDepartInputModel.toDepartment()))
    }

    /**
     * Updates a department.
     *
     * @param updateDepartInputModel the department to update
     *
     * @return the [CreationDepartmentResult] if updated, [SaveUpdateDepartmentError] otherwise
     */
    @Transactional
    fun update(updateDepartInputModel: UpdateDepartmentInputModel): CreationDepartmentResult {
        if (updateDepartInputModel.name.isBlank()) {
            return failure(SaveUpdateDepartmentError.IncorrectNameDepartment)
        }

        return departmentRepository.findById(updateDepartInputModel.id)
            .map<CreationDepartmentResult> { depart ->
                success(
                    departmentRepository.save(
                        depart.copy(
                            name = updateDepartInputModel.name
                        )
                    )
                )
            }.orElse(failure(SaveUpdateDepartmentError.NotFoundDepartment))
    }

    /**
     * Deletes a department by its id.
     *
     * @param id the department id
     *
     * @return the [DeleteDepartmentResult] if deleted, [DeleteDepartmentError] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteDepartmentResult =
        departmentRepository.findById(id)
            .map<DeleteDepartmentResult> { depart ->
                departmentRepository.delete(depart)
                success(depart)
            }.orElse(failure(DeleteDepartmentError.NotFoundDepartment))
}
