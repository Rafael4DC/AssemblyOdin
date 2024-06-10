package pt.isel.odin.service.fieldstudy

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyInputModel
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.service.fieldstudy.error.DeleteFieldStudyError
import pt.isel.odin.service.fieldstudy.error.GetFieldStudyError
import pt.isel.odin.service.fieldstudy.error.SaveUpdateFieldStudyError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

@Service
class FieldStudyService(
    private val fieldStudyRepository: FieldStudyRepository,
    private val departmentRepository: DepartmentRepository
) {

    fun getById(id: Long): GetFieldStudyResult =
        fieldStudyRepository.findById(id)
            .map<GetFieldStudyResult> { field -> success(field) }
            .orElse(failure(GetFieldStudyError.NotFoundFieldStudy))

    fun getAll(): GetAllFieldsStudyResult = success(fieldStudyRepository.findAll())

    @Transactional
    fun save(saveFieldStudyInputModel: SaveFieldStudyInputModel): CreationFieldStudyResult {
        if (fieldStudyRepository.findByName(saveFieldStudyInputModel.name).isPresent)
            return failure(SaveUpdateFieldStudyError.AlreadyExistsFieldStudy)

        val department = departmentRepository.findById(saveFieldStudyInputModel.department)
        if (department.isEmpty) return failure(SaveUpdateFieldStudyError.NotFoundDepartment)

        return success(fieldStudyRepository.save(saveFieldStudyInputModel.toFieldStudy(department.get())))
    }

    @Transactional
    fun update(updateFieldStudyInputModel: UpdateFieldStudyInputModel): CreationFieldStudyResult {
        val department = departmentRepository.findById(updateFieldStudyInputModel.department)
        if (department.isEmpty) return failure(SaveUpdateFieldStudyError.NotFoundDepartment)

        return fieldStudyRepository.findById(updateFieldStudyInputModel.id)
            .map<CreationFieldStudyResult> { field ->
                success(
                    fieldStudyRepository.save(
                        field.copy(
                            name = updateFieldStudyInputModel.name,
                            department = department.get(),
                        )
                    )
                )
            }.orElse(failure(SaveUpdateFieldStudyError.NotFoundFieldStudy))
    }

    @Transactional
    fun delete(id: Long): DeleteFieldStudyResult =
        fieldStudyRepository.findById(id)
            .map<DeleteFieldStudyResult> { field ->
                fieldStudyRepository.delete(field)
                success(field)
            }.orElse(failure(DeleteFieldStudyError.NotFoundFieldStudy))
}
