package pt.isel.odin.service.voc

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.voc.models.SaveVocInputModel
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.model.Module
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.repository.VocRepository
import pt.isel.odin.service.voc.error.DeleteVocError
import pt.isel.odin.service.voc.error.GetVocError
import pt.isel.odin.service.voc.error.SaveUpdateVocError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success
import java.time.LocalDateTime

@Service
class VocService(
    private val vocRepository: VocRepository,
    private val userRepository: UserRepository,
    private val moduleRepository: ModuleRepository,
) {

    fun getById(id: Long): GetVocResult =
        vocRepository.findById(id)
            .map<GetVocResult> { voc -> success(voc) }
            .orElse(failure(GetVocError.NotFoundVoc))

    fun getAll(): GetAllVocsResult = success(vocRepository.findAll())

    @Transactional
    fun save(saveVocInputModel: SaveVocInputModel, email: String): CreationVocResult {
        val user = getUser(saveVocInputModel.user, email) ?: return failure(SaveUpdateVocError.NotFoundUser)
        val module = getModule(saveVocInputModel.module) ?: return failure(SaveUpdateVocError.NotFoundModule)

        return success(vocRepository.save(saveVocInputModel.toVoc(user, module)))
    }

    @Transactional
    fun update(updateVocInputModel: UpdateVocInputModel, email: String): CreationVocResult {
        val user = getUser(updateVocInputModel.user, email) ?: return failure(SaveUpdateVocError.NotFoundUser)
        val module = getModule(updateVocInputModel.module) ?: return failure(SaveUpdateVocError.NotFoundModule)

        return vocRepository.findById(updateVocInputModel.id)
            .map<CreationVocResult> { voc ->
                success(
                    vocRepository.save(
                        voc.copy(
                            approved = updateVocInputModel.approved,
                            user = user,
                            module = module,
                            started = LocalDateTime.parse(updateVocInputModel.started),
                            ended = LocalDateTime.parse(updateVocInputModel.ended)
                        )
                    )
                )
            }.orElse(failure(SaveUpdateVocError.NotFoundVoc))
    }

    @Transactional
    fun delete(id: Long): DeleteVocResult =
        vocRepository.findById(id)
            .map<DeleteVocResult> { voc ->
                vocRepository.delete(voc)
                success(voc)
            }.orElse(failure(DeleteVocError.NotFoundVoc))

    /*fun getByStudent(email: String): List<Voc> {
        val student = userService.getByEmail(email)
        return vocRepository.findByUserId(1)
    }*/

    private fun getUser(userId: Long?, email: String): User? {
        val user = if (userId == null)
            userRepository.findByEmail(email)
        else
            userRepository.findById(userId)
        return if (user.isEmpty) null
        else user.get()
    }

    private fun getModule(moduleId: Long): Module? {
        val module = moduleRepository.findById(moduleId)
        return if (module.isEmpty) null
        else module.get()
    }
}
