package pt.isel.odin.service.section

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.section.models.SaveSectionInputModel
import pt.isel.odin.http.controllers.section.models.UpdateSectionInputModel
import pt.isel.odin.model.Module
import pt.isel.odin.model.Section
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.section.error.DeleteSectionError
import pt.isel.odin.service.section.error.GetSectionError
import pt.isel.odin.service.section.error.SaveUpdateSectionError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success

/**
 * Service for Sections.
 */
@Service
class SectionService(
    private val sectionRepository: SectionRepository,
    private val userRepository: UserRepository,
    private val moduleRepository: ModuleRepository
) {

    /**
     * Gets a section by its id.
     *
     * @param id the section id
     *
     * @return the [GetSectionResult] if found, [GetSectionError.NotFoundSection] otherwise
     */
    fun getById(id: Long): GetSectionResult =
        sectionRepository.findById(id)
            .map<GetSectionResult> { section -> success(section) }
            .orElse(failure(GetSectionError.NotFoundSection))

    /**
     * Gets all sections.
     *
     * @return the [GetAllSectionsResult] with the list of [Section]
     */
    fun getAll(): GetAllSectionsResult = success(sectionRepository.findAll())

    /**
     * Saves a section.
     *
     * @param saveSectionInputModel the section to save
     *
     * @return the [CreationSectionResult] if saved, [SaveUpdateSectionError] otherwise
     */
    @Transactional
    fun save(saveSectionInputModel: SaveSectionInputModel): CreationSectionResult {
        if (saveSectionInputModel.name.isBlank()) {
            return failure(SaveUpdateSectionError.IncorrectNameSection)
        }

        if (sectionRepository.findByName(saveSectionInputModel.name).isPresent) {
            return failure(SaveUpdateSectionError.AlreadyExistsSection)
        }

        val module = getModule(saveSectionInputModel.module) ?: return failure(SaveUpdateSectionError.NotFoundModule)

        val studentsInSec = userRepository.findAllById(saveSectionInputModel.students)

        return success(sectionRepository.save(saveSectionInputModel.toSection(studentsInSec, module)))
    }

    /**
     * Updates a section.
     *
     * @param updateSectionInputModel the section to update
     *
     * @return the [CreationSectionResult] if updated, [SaveUpdateSectionError] otherwise
     */
    @Transactional
    fun update(updateSectionInputModel: UpdateSectionInputModel): CreationSectionResult {
        if (updateSectionInputModel.name.isBlank()) {
            return failure(SaveUpdateSectionError.IncorrectNameSection)
        }

        val module = getModule(updateSectionInputModel.module) ?: return failure(SaveUpdateSectionError.NotFoundModule)
        val studentsInSec = userRepository.findAllById(updateSectionInputModel.students)

        return sectionRepository.findById(updateSectionInputModel.id)
            .map<CreationSectionResult> { section ->
                success(
                    sectionRepository.save(
                        section.copy(
                            name = updateSectionInputModel.name,
                            module = module,
                            students = studentsInSec
                        )
                    )
                )
            }.orElse(failure(SaveUpdateSectionError.NotFoundSection))
    }

    /**
     * Deletes a section by its id.
     *
     * @param id the section id
     *
     * @return the [DeleteSectionResult] if deleted, [DeleteSectionError] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteSectionResult =
        sectionRepository.findById(id)
            .map<DeleteSectionResult> { section ->
                sectionRepository.delete(section)
                success(section)
            }.orElse(failure(DeleteSectionError.NotFoundSection))


    private fun getModule(moduleId: Long): Module? {
        val module = moduleRepository.findById(moduleId)
        return if (module.isEmpty) {
            null
        } else {
            module.get()
        }
    }
}
