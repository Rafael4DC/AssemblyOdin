package pt.isel.odin.service.tech

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechInputModel
import pt.isel.odin.model.Module
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.tech.error.DeleteTechError
import pt.isel.odin.service.tech.error.GetTechError
import pt.isel.odin.service.tech.error.SaveUpdateTechError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success
import java.time.LocalDateTime

@Service
class TechService(
    private val techRepository: TechRepository,
    private val userRepository: UserRepository,
    private val moduleRepository: ModuleRepository
) {

    fun getById(id: Long): GetTechResult =
        techRepository.findById(id)
            .map<GetTechResult> { tech -> success(tech) }
            .orElse(failure(GetTechError.NotFoundTech))

    fun getAll(): GetAllTechsResult = success(techRepository.findAll())

    @Transactional
    fun save(saveTechInputModel: SaveTechInputModel, email: String): CreationTechResult {
        val user = getUser(saveTechInputModel.teacher, email) ?: return failure(SaveUpdateTechError.NotFoundUser)
        val module = getModule(saveTechInputModel.module) ?: return failure(SaveUpdateTechError.NotFoundModule)

        val studentsInSec = userRepository.findAllById(saveTechInputModel.missTech)

        return success(techRepository.save(saveTechInputModel.toTech(user, module, studentsInSec)))
    }

    @Transactional
    fun update(updateTechInputModel: UpdateTechInputModel, email: String): CreationTechResult {
        val user = getUser(updateTechInputModel.teacher, email) ?: return failure(SaveUpdateTechError.NotFoundUser)
        val module = getModule(updateTechInputModel.module) ?: return failure(SaveUpdateTechError.NotFoundModule)

        val studentsInSec = userRepository.findAllById(updateTechInputModel.missTech)

        return techRepository.findById(updateTechInputModel.id)
            .map<CreationTechResult> { tech ->
                success(
                    techRepository.save(
                        tech.copy(
                            teacher = user,
                            module = module,
                            date = LocalDateTime.parse(updateTechInputModel.date),
                            summary = updateTechInputModel.summary,
                            missTech = studentsInSec
                        )
                    )
                )
            }.orElse(failure(SaveUpdateTechError.NotFoundTech))
    }

    @Transactional
    fun delete(id: Long): DeleteTechResult =
        techRepository.findById(id)
            .map<DeleteTechResult> { tech ->
                techRepository.delete(tech)
                success(tech)
            }.orElse(failure(DeleteTechError.NotFoundTech))

    fun getByUser(email: String): List<Tech> {
        /*val user = userService.getByEmail(email) ?: throw NotFoundException("No User Found")
        return if (user.role?.name == "STUDENT") {
            techRepository.getByStudentId(user.id!!)
        } else {
            techRepository.getByUserId(user.id!!)
        }*/
        return emptyList()
    }

    /*fun getMyTechsAttendance(email: String): List<TechAttendanceResponse> {
        val techs = getByUser(email)
        return techs.map { tech ->
            TechAttendanceResponse(
                tech,
                missTechService.getById(tech.id!!)
            )
        }
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
