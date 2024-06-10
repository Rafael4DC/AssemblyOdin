package pt.isel.odin.service.department

import jakarta.transaction.Transactional
import org.hibernate.internal.CoreLogging.logger
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.department.models.SaveDepartmentInputModel
import pt.isel.odin.http.controllers.department.models.UpdateDepartmentInputModel
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.service.department.error.DeleteDepartmentError
import pt.isel.odin.service.department.error.GetDepartmentError
import pt.isel.odin.service.department.error.SaveUpdateDepartmentError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

@Service
class DepartmentService(private val departmentRepository: DepartmentRepository) {

    fun getById(id: Long): GetDepartmentResult =
        departmentRepository.findById(id)
            .map<GetDepartmentResult> { depart -> success(depart) }
            .orElse(failure(GetDepartmentError.NotFoundDepartment))

    fun getAll(): GetAllDepartmentsResult = success(departmentRepository.findAll())

    @Transactional
    fun save(saveDepartInputModel: SaveDepartmentInputModel): CreationDepartmentResult {
        if (departmentRepository.findByName(saveDepartInputModel.name).isPresent)
            return failure(SaveUpdateDepartmentError.AlreadyExistsDepartment)

        return success(departmentRepository.save(saveDepartInputModel.toDepartment()))
    }

    @Transactional
    fun update(updateDepartInputModel: UpdateDepartmentInputModel): CreationDepartmentResult =
        departmentRepository.findById(updateDepartInputModel.id)
            .map<CreationDepartmentResult> { depart ->
                success(
                    departmentRepository.save(
                        depart.copy(
                            name = updateDepartInputModel.name
                        )
                    )
                )
            }.orElse(failure(SaveUpdateDepartmentError.NotFoundDepartment))


    @Transactional
    fun delete(id: Long): DeleteDepartmentResult =
        departmentRepository.findById(id)
            .map<DeleteDepartmentResult> { depart ->
                departmentRepository.delete(depart)
                success(depart)
            }.orElse(failure(DeleteDepartmentError.NotFoundDepartment))

    companion object {
        private val log = logger(DepartmentService::class.java)
    }
}
