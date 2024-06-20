package pt.isel.odin.service.fieldstudy

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.fieldstudy.models.SaveFieldStudyInputModel
import pt.isel.odin.http.controllers.fieldstudy.models.UpdateFieldStudyInputModel
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.service.fieldstudy.error.DeleteFieldStudyError
import pt.isel.odin.service.fieldstudy.error.GetFieldStudyError
import pt.isel.odin.service.fieldstudy.error.SaveUpdateFieldStudyError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

/**
 * Service for Fields Of Study.
 */
@Service
class FieldStudyService(
    private val fieldStudyRepository: FieldStudyRepository,
    private val departmentRepository: DepartmentRepository
) {

    /**
     * Gets a field of study by its id.
     *
     * @param id the field of study id
     *
     * @return the [GetFieldStudyResult] if found, [GetFieldStudyError.NotFoundFieldStudy] otherwise
     */
    fun getById(id: Long): GetFieldStudyResult =
        fieldStudyRepository.findById(id)
            .map<GetFieldStudyResult> { field -> success(field) }
            .orElse(failure(GetFieldStudyError.NotFoundFieldStudy))

    /**
     * Gets all fields of study.
     *
     * @return the [GetAllFieldsStudyResult] with the list of [FieldStudy]
     */
    fun getAll(): GetAllFieldsStudyResult = success(fieldStudyRepository.findAll())

    /**
     * Saves a field of study.
     *
     * @param saveFieldStudyInputModel the field of study to save
     *
     * @return the [CreationFieldStudyResult] if saved, [SaveUpdateFieldStudyError] otherwise
     */
    @Transactional
    fun save(saveFieldStudyInputModel: SaveFieldStudyInputModel): CreationFieldStudyResult {
        if (fieldStudyRepository.findByName(saveFieldStudyInputModel.name).isPresent) {
            return failure(SaveUpdateFieldStudyError.AlreadyExistsFieldStudy)
        }

        val department = departmentRepository.findById(saveFieldStudyInputModel.department)
        if (department.isEmpty) return failure(SaveUpdateFieldStudyError.NotFoundDepartment)

        if (saveFieldStudyInputModel.name.isBlank()) {
            return failure(SaveUpdateFieldStudyError.IncorrectNameFieldStudy)
        }

        return success(fieldStudyRepository.save(saveFieldStudyInputModel.toFieldStudy(department.get())))
    }

    /**
     * Updates a field of study.
     *
     * @param updateFieldStudyInputModel the field of study to update
     *
     * @return the [CreationFieldStudyResult] if updated, [SaveUpdateFieldStudyError] otherwise
     */
    @Transactional
    fun update(updateFieldStudyInputModel: UpdateFieldStudyInputModel): CreationFieldStudyResult {
        val department = departmentRepository.findById(updateFieldStudyInputModel.department)
        if (department.isEmpty) return failure(SaveUpdateFieldStudyError.NotFoundDepartment)

        if (updateFieldStudyInputModel.name.isBlank()) {
            return failure(SaveUpdateFieldStudyError.IncorrectNameFieldStudy)
        }

        return fieldStudyRepository.findById(updateFieldStudyInputModel.id)
            .map<CreationFieldStudyResult> { field ->
                success(
                    fieldStudyRepository.save(
                        field.copy(
                            name = updateFieldStudyInputModel.name,
                            department = department.get()
                        )
                    )
                )
            }.orElse(failure(SaveUpdateFieldStudyError.NotFoundFieldStudy))
    }

    /**
     * Deletes a field of study by its id.
     *
     * @param id the field of study id
     *
     * @return the [DeleteFieldStudyResult] if deleted, [DeleteFieldStudyError] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteFieldStudyResult =
        fieldStudyRepository.findById(id)
            .map<DeleteFieldStudyResult> { field ->
                fieldStudyRepository.delete(field)
                success(field)
            }.orElse(failure(DeleteFieldStudyError.NotFoundFieldStudy))
}
