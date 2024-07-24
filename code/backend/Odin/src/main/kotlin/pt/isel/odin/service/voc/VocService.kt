package pt.isel.odin.service.voc

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.voc.models.SaveVocInputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.model.Voc
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.repository.VocRepository
import pt.isel.odin.service.ServiceUtils
import pt.isel.odin.service.voc.error.DeleteVocError
import pt.isel.odin.service.voc.error.GetVocError
import pt.isel.odin.service.voc.error.SaveUpdateVocError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success
import java.time.LocalDateTime

/**
 * Service for Vocs.
 */
@Service
class VocService(
    private val vocRepository: VocRepository,
    private val userRepository: UserRepository,
    private val serviceUtils: ServiceUtils
) {

    /**
     * Gets a voc by its id.
     *
     * @param id the voc id
     *
     * @return the [GetVocResult] if found, [GetVocError.NotFoundVoc] otherwise
     */
    fun getById(id: Long): GetVocResult =
        vocRepository.findById(id)
            .map<GetVocResult> { voc -> success(voc) }
            .orElse(failure(GetVocError.NotFoundVoc))

    /**
     * Gets all vocs.
     *
     * @return the [GetAllVocsResult] with the list of [Voc]
     */
    fun getAll(): GetAllVocsResult = success(vocRepository.findAll())

    /**
     * Saves a voc.
     *
     * @param saveVocInputModel the voc to save
     * @param email the email of the user
     *
     * @return the [CreationVocResult] if saved, [SaveUpdateVocError] otherwise
     */
    @Transactional
    fun save(saveVocInputModel: SaveVocInputModel, email: String): CreationVocResult {
        val user =
            serviceUtils.getUser(saveVocInputModel.user, email) ?: return failure(SaveUpdateVocError.NotFoundUser)
        val section =
            serviceUtils.getSection(saveVocInputModel.section) ?: return failure(SaveUpdateVocError.NotFoundSection)

        return success(vocRepository.save(saveVocInputModel.toVoc(user, section)))
    }

    /**
     * Updates a voc.
     *
     * @param updateVocInputModel the voc to update
     * @param email the email of the user
     *
     * @return the [CreationVocResult] if updated, [SaveUpdateVocError] otherwise
     */
    @Transactional
    fun update(updateVocInputModel: UpdateVocInputModel, email: String): CreationVocResult {
        val user =
            serviceUtils.getUser(updateVocInputModel.user, email) ?: return failure(SaveUpdateVocError.NotFoundUser)
        val section =
            serviceUtils.getSection(updateVocInputModel.section) ?: return failure(SaveUpdateVocError.NotFoundSection)

        return vocRepository.findById(updateVocInputModel.id)
            .map<CreationVocResult> { voc ->
                serviceUtils.vocPointsToUser(user, updateVocInputModel, voc)
                success(
                    vocRepository.save(
                        voc.copy(
                            approved = updateVocInputModel.approved,
                            description = updateVocInputModel.description,
                            user = user,
                            section = section,
                            started = LocalDateTime.parse(updateVocInputModel.started),
                            ended = LocalDateTime.parse(updateVocInputModel.ended)
                        )
                    )
                )
            }.orElse(failure(SaveUpdateVocError.NotFoundVoc))
    }

    /**
     * Deletes a voc.
     *
     * @param id the voc id
     *
     * @return the [DeleteVocResult] if deleted, [DeleteVocError.NotFoundVoc] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteVocResult =
        vocRepository.findById(id)
            .map<DeleteVocResult> { voc ->
                vocRepository.delete(voc)
                success(voc)
            }.orElse(failure(DeleteVocError.NotFoundVoc))

    @Transactional
    fun getByUser(email: String): GetAllVocsResult {
        return userRepository.findByEmail(email)
            .map { user ->
                vocRepository.findByUser(user)
                    .map<GetAllVocsResult> { success(it) }
                    .orElse(failure(GetVocError.NotFoundVoc))
            }.orElse(failure(GetVocError.NotFoundUser))
    }
}
