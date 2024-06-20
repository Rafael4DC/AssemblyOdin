package pt.isel.odin.service.module

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.module.models.SaveModuleInputModel
import pt.isel.odin.http.controllers.module.models.UpdateModuleInputModel
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.service.module.error.DeleteModuleError
import pt.isel.odin.service.module.error.GetModuleError
import pt.isel.odin.service.module.error.SaveUpdateModuleError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

/**
 * Service for Modules.
 */
@Service
class ModuleService(
    private val moduleRepository: ModuleRepository,
    private val fieldStudyRepository: FieldStudyRepository
) {

    /**
     * Gets a module by its id.
     *
     * @param id the module id
     *
     * @return the [GetModuleResult] if found, [GetModuleError.NotFoundModule] otherwise
     */
    fun getById(id: Long): GetModuleResult =
        moduleRepository.findById(id)
            .map<GetModuleResult> { module -> success(module) }
            .orElse(failure(GetModuleError.NotFoundModule))

    /**
     * Gets all modules.
     *
     * @return the [GetAllModulesResult] with the list of [Module]
     */
    fun getAll(): GetAllModulesResult = success(moduleRepository.findAll())

    /**
     * Saves a module.
     *
     * @param saveModuleInputModel the module to save
     *
     * @return the [CreationModuleResult] if saved, [SaveUpdateModuleError] otherwise
     */
    @Transactional
    fun save(saveModuleInputModel: SaveModuleInputModel): CreationModuleResult {
        if (saveModuleInputModel.name.isBlank()) {
            return failure(SaveUpdateModuleError.IncorrectNameModule)
        }

        if (moduleRepository.findByName(saveModuleInputModel.name).isPresent) {
            return failure(SaveUpdateModuleError.AlreadyExistsModule)
        }

        val fieldStudy = fieldStudyRepository.findById(saveModuleInputModel.fieldStudy)
        if (fieldStudy.isEmpty) return failure(SaveUpdateModuleError.NotFoundFieldStudy)

        return success(moduleRepository.save(saveModuleInputModel.toModule(fieldStudy.get())))
    }

    /**
     * Updates a module.
     *
     * @param updateModuleInputModel the module to update
     *
     * @return the [CreationModuleResult] if updated, [SaveUpdateModuleError] otherwise
     */
    @Transactional
    fun update(updateModuleInputModel: UpdateModuleInputModel): CreationModuleResult {
        if (updateModuleInputModel.name.isBlank()) {
            return failure(SaveUpdateModuleError.IncorrectNameModule)
        }

        val fieldStudy = fieldStudyRepository.findById(updateModuleInputModel.fieldStudy)
        if (fieldStudy.isEmpty) return failure(SaveUpdateModuleError.NotFoundFieldStudy)

        return moduleRepository.findById(updateModuleInputModel.id)
            .map<CreationModuleResult> { module ->
                success(
                    moduleRepository.save(
                        module.copy(
                            name = updateModuleInputModel.name,
                            fieldStudy = fieldStudy.get(),
                            tier = updateModuleInputModel.tier
                        )
                    )
                )
            }.orElse(failure(SaveUpdateModuleError.NotFoundModule))
    }

    /**
     * Deletes a module by its id.
     *
     * @param id the module id
     *
     * @return the [DeleteModuleResult] if deleted, [DeleteModuleError] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteModuleResult =
        moduleRepository.findById(id)
            .map<DeleteModuleResult> { module ->
                moduleRepository.delete(module)
                success(module)
            }.orElse(failure(DeleteModuleError.NotFoundModule))
}
