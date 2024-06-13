package pt.isel.odin.service.section

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.section.models.SaveSectionInputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionInputModel
import pt.isel.odin.model.Module
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.section.error.DeleteSectionError
import pt.isel.odin.service.section.error.GetSectionError
import pt.isel.odin.service.section.error.SaveUpdateSectionError
import pt.isel.odin.service.tech.error.SaveUpdateTechError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

@Service
class SectionService(
    private val sectionRepository: SectionRepository,
    private val userRepository: UserRepository,
    private val moduleRepository: ModuleRepository
) {

    fun getById(id: Long): GetSectionResult =
        sectionRepository.findById(id)
            .map<GetSectionResult> { section -> success(section) }
            .orElse(failure(GetSectionError.NotFoundSection))

    fun getAll(): GetAllSectionsResult = success(sectionRepository.findAll())

    @Transactional
    fun save(saveSectionInputModel: SaveSectionInputModel): CreationSectionResult {
        if (sectionRepository.findByName(saveSectionInputModel.name).isPresent)
            return failure(SaveUpdateSectionError.AlreadyExistsSection)

        val module = getModule(saveSectionInputModel.module) ?: return failure(SaveUpdateSectionError.NotFoundModule)

        val studentsInSec = userRepository.findAllById(saveSectionInputModel.students)

        return success(sectionRepository.save(saveSectionInputModel.toSection(studentsInSec, module)))
    }

    @Transactional
    fun update(updateSectionInputModel: UpdateSectionInputModel): CreationSectionResult {
        val module = getModule(updateSectionInputModel.module) ?: return failure(SaveUpdateSectionError.NotFoundModule)
        val studentsInSec = userRepository.findAllById(updateSectionInputModel.students)

        return sectionRepository.findById(updateSectionInputModel.id)
            .map<CreationSectionResult> { section ->
                success(
                    sectionRepository.save(
                        section.copy(
                            name = updateSectionInputModel.name,
                            summary = updateSectionInputModel.summary,
                            module = module,
                            students = studentsInSec
                        )
                    )
                )
            }.orElse(failure(SaveUpdateSectionError.NotFoundSection))
    }

    @Transactional
    fun delete(id: Long): DeleteSectionResult =
        sectionRepository.findById(id)
            .map<DeleteSectionResult> { section ->
                sectionRepository.delete(section)
                success(section)
            }.orElse(failure(DeleteSectionError.NotFoundSection))


    private fun getModule(moduleId: Long): Module? {
        val module = moduleRepository.findById(moduleId)
        return if (module.isEmpty) null
        else module.get()
    }
}
