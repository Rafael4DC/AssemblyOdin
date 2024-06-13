package pt.isel.odin.service.tech

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.UpdateTechInputModel
import pt.isel.odin.model.Role
import pt.isel.odin.model.Section
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.tech.error.DeleteTechError
import pt.isel.odin.service.tech.error.GetTechError
import pt.isel.odin.service.tech.error.SaveUpdateTechError
import pt.isel.odin.service.voc.error.GetVocError
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success
import java.time.LocalDateTime

@Service
class TechService(
    private val techRepository: TechRepository,
    private val userRepository: UserRepository,
    private val sectionRepository: SectionRepository
) {

    fun getById(id: Long): GetTechResult =
        techRepository.findById(id)
            .map<GetTechResult> { tech -> success(tech) }
            .orElse(failure(GetTechError.NotFoundTech))

    fun getAll(): GetAllTechsResult = success(techRepository.findAll())

    @Transactional
    fun save(saveTechInputModel: SaveTechInputModel, email: String): CreationTechResult {
        val user = getUser(saveTechInputModel.teacher, email) ?: return failure(SaveUpdateTechError.NotFoundUser)
        val section = getSection(saveTechInputModel.section) ?: return failure(SaveUpdateTechError.NotFoundSection)

        val studentsInSec = userRepository.findAllById(saveTechInputModel.missTech)

        return success(techRepository.save(saveTechInputModel.toTech(user, section, studentsInSec)))
    }

    @Transactional
    fun update(updateTechInputModel: UpdateTechInputModel, email: String): CreationTechResult {
        val user = getUser(updateTechInputModel.teacher, email) ?: return failure(SaveUpdateTechError.NotFoundUser)
        val section = getSection(updateTechInputModel.section) ?: return failure(SaveUpdateTechError.NotFoundSection)

        val studentsInSec = userRepository.findAllById(updateTechInputModel.missTech)

        return techRepository.findById(updateTechInputModel.id)
            .map<CreationTechResult> { tech ->
                success(
                    techRepository.save(
                        tech.copy(
                            teacher = user,
                            section = section,
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

    fun getByUser(email: String): GetAllTechsResult {
        val user = getUser(email = email) ?: return failure(GetTechError.NotFoundUser)// Diferenciar Teacher de Student
        val optionalTech =
            if (Role.RoleEnum.valueOf(user.role.name!!) == Role.RoleEnum.TEACHER)
                techRepository.findByTeacher(user)
            else
                techRepository.findByStudent(user.id!!)

        return optionalTech
            .map { success(it) }
            .orElse(success(emptyList()))
    }

    private fun getUser(userId: Long? = null, email: String): User? {
        val user = if (userId == null || userId == 0L)
            userRepository.findByEmail(email)
        else
            userRepository.findById(userId)
        return if (user.isEmpty) null
        else user.get()
    }

    private fun getSection(sectionId: Long): Section? {
        val section = sectionRepository.findById(sectionId)
        return if (section.isEmpty) null
        else section.get()
    }
}
