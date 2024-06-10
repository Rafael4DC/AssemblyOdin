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

@Service
class ModuleService(
    private val moduleRepository: ModuleRepository,
    private val fieldStudyRepository: FieldStudyRepository
) {

    fun getById(id: Long): GetModuleResult =
        moduleRepository.findById(id)
            .map<GetModuleResult> { module -> success(module) }
            .orElse(failure(GetModuleError.NotFoundModule))

    fun getAll(): GetAllModulesResult = success(moduleRepository.findAll())

    @Transactional
    fun save(saveModuleInputModel: SaveModuleInputModel): CreationModuleResult {
        if (moduleRepository.findByName(saveModuleInputModel.name).isPresent)
            return failure(SaveUpdateModuleError.AlreadyExistsModule)

        val fieldStudy = fieldStudyRepository.findById(saveModuleInputModel.fieldStudy)
        if (fieldStudy.isEmpty) return failure(SaveUpdateModuleError.NotFoundFieldStudy)

        return success(moduleRepository.save(saveModuleInputModel.toModule(fieldStudy.get())))
    }

    @Transactional
    fun update(updateModuleInputModel: UpdateModuleInputModel): CreationModuleResult {
        val fieldStudy = fieldStudyRepository.findById(updateModuleInputModel.fieldStudy)
        if (fieldStudy.isEmpty) return failure(SaveUpdateModuleError.NotFoundFieldStudy)

        return moduleRepository.findById(updateModuleInputModel.id)
            .map<CreationModuleResult> { module ->
                success(
                    moduleRepository.save(
                        module.copy(
                            name = updateModuleInputModel.name,
                            fieldStudy = fieldStudy.get(),
                            tier = updateModuleInputModel.tier,
                        )
                    )
                )
            }.orElse(failure(SaveUpdateModuleError.NotFoundModule))
    }

    @Transactional
    fun delete(id: Long): DeleteModuleResult =
        moduleRepository.findById(id)
            .map<DeleteModuleResult> { module ->
                moduleRepository.delete(module)
                success(module)
            }.orElse(failure(DeleteModuleError.NotFoundModule))
}
