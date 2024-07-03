package pt.isel.odin.service.tech

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.tech.models.SaveTechInputModel
import pt.isel.odin.http.controllers.tech.models.SaveScheduleTechInputModel
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
import pt.isel.odin.utils.failure
import pt.isel.odin.utils.success
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

/**
 * Service for Techs.
 */
@Service
class TechService(
    private val techRepository: TechRepository,
    private val userRepository: UserRepository,
    private val sectionRepository: SectionRepository
) {

    /**
     * Gets a tech by its id.
     *
     * @param id the tech id
     *
     * @return the [GetTechResult] if found, [GetTechError.NotFoundTech] otherwise
     */
    fun getById(id: Long): GetTechResult =
        techRepository.findById(id)
            .map<GetTechResult> { tech -> success(tech) }
            .orElse(failure(GetTechError.NotFoundTech))

    /**
     * Gets all techs.
     *
     * @return the [GetAllTechsResult] with the list of [Tech]
     */
    fun getAll(): GetAllTechsResult = success(techRepository.findAll())

    /**
     * Saves a tech.
     *
     * @param saveTechInputModel the tech to save
     * @param email the email of the user
     *
     * @return the [CreationTechResult] if saved, [SaveUpdateTechError] otherwise
     */
    @Transactional
    fun save(saveTechInputModel: SaveTechInputModel, email: String): CreationTechResult {
        val user = getUser(saveTechInputModel.teacher, email) ?: return failure(SaveUpdateTechError.NotFoundUser)
        val section = getSection(saveTechInputModel.section) ?: return failure(SaveUpdateTechError.NotFoundSection)

        val studentsMissTech = userRepository.findAllById(saveTechInputModel.missTech)

        return success(techRepository.save(saveTechInputModel.toTech(user, section, studentsMissTech)))
    }

    /**
     * Saves multiple classes.
     *
     * @param input the tech to save
     * @param email the email of the user
     *
     * @return the [CreationTechResult] if saved, [SaveUpdateTechError] otherwise
     */
    @Transactional
    fun saveMultipleClasses(input: SaveScheduleTechInputModel, email: String): List<CreationTechResult> {
        val user = getUser(input.teacher, email) ?: return listOf(failure(SaveUpdateTechError.NotFoundUser))
        val section = getSection(input.section) ?: return listOf(failure(SaveUpdateTechError.NotFoundSection))

        val results = mutableListOf<CreationTechResult>()

        var current = input.startDate.with(TemporalAdjusters.nextOrSame(input.dayOfWeek))
        while (!current.isAfter(input.endDate)) {
            val startDateTime = LocalDateTime.of(current, input.classTime)
            val endDateTime = startDateTime.plusHours(input.classLengthHours)

            val tech = Tech(
                teacher = user,
                section = section,
                started = startDateTime,
                ended = endDateTime,
            )

            results.add(success(techRepository.save(tech)))
            current = current.plus(1, ChronoUnit.WEEKS)
        }

        return results
    }

    /**
     * Updates a tech.
     *
     * @param updateTechInputModel the tech to update
     * @param email the email of the user
     *
     * @return the [CreationTechResult] if updated, [SaveUpdateTechError] otherwise
     */
    @Transactional
    fun update(updateTechInputModel: UpdateTechInputModel, email: String): CreationTechResult {
        val user = getUser(updateTechInputModel.teacher, email) ?: return failure(SaveUpdateTechError.NotFoundUser)
        val section = getSection(updateTechInputModel.section) ?: return failure(SaveUpdateTechError.NotFoundSection)

        val studentsMissTech = userRepository.findAllById(updateTechInputModel.missTech)

        return techRepository.findById(updateTechInputModel.id)
            .map<CreationTechResult> { tech ->
                success(
                    techRepository.save(
                        tech.copy(
                            teacher = user,
                            section = section,
                            started = LocalDateTime.parse(updateTechInputModel.started),
                            ended = LocalDateTime.parse(updateTechInputModel.ended),
                            summary = updateTechInputModel.summary,
                            missTech = studentsMissTech
                        )
                    )
                )
            }.orElse(failure(SaveUpdateTechError.NotFoundTech))
    }

    /**
     * Updates multiple classes.
     *
     * @param input the tech to update
     * @param email the email of the user
     *
     * @return the [CreationTechResult] if updated, [SaveUpdateTechError] otherwise
     */
    @Transactional
    fun updateMultipleClasses(input: SaveScheduleTechInputModel, email: String): List<CreationTechResult> {
        val user = getUser(input.teacher, email) ?: return listOf(failure(SaveUpdateTechError.NotFoundUser))
        val section = getSection(input.section) ?: return listOf(failure(SaveUpdateTechError.NotFoundSection))

        val results = mutableListOf<CreationTechResult>()

        var current = input.startDate.with(TemporalAdjusters.nextOrSame(input.dayOfWeek))
        while (!current.isAfter(input.endDate)) {
            val startDateTime = LocalDateTime.of(current, input.classTime)
            val endDateTime = startDateTime.plusHours(input.classLengthHours)

            val tech = Tech(
                teacher = user,
                section = section,
                started = startDateTime,
                ended = endDateTime,
            )

            results.add(success(techRepository.save(tech)))
            current = current.plus(1, ChronoUnit.WEEKS)
        }

        return results
    }

    /**
     * Deletes a tech by its id.
     *
     * @param id the tech id
     *
     * @return the [DeleteTechResult] if deleted, [DeleteTechError.NotFoundTech] otherwise
     */
    @Transactional
    fun delete(id: Long): DeleteTechResult =
        techRepository.findById(id)
            .map<DeleteTechResult> { tech ->
                techRepository.delete(tech)
                success(tech)
            }.orElse(failure(DeleteTechError.NotFoundTech))

    fun getByUser(email: String): GetAllTechsResult {
        val user = getUser(email = email) ?: return failure(GetTechError.NotFoundUser) // Diferenciar Teacher de Student
        val optionalTech =
            if (Role.RoleEnum.valueOf(user.role.name!!) == Role.RoleEnum.TEACHER) {
                techRepository.findByTeacher(user)
            } else {
                techRepository.findByStudent(user.id!!)
            }

        return optionalTech
            .map { success(it) }
            .orElse(success(emptyList()))
    }

    private fun getUser(userId: Long? = null, email: String): User? {
        val user = if (userId == null || userId == 0L) {
            userRepository.findByEmail(email)
        } else {
            userRepository.findById(userId)
        }
        return if (user.isEmpty) {
            null
        } else {
            user.get()
        }
    }

    private fun getSection(sectionId: Long): Section? {
        val section = sectionRepository.findById(sectionId)
        return if (section.isEmpty) {
            null
        } else {
            section.get()
        }
    }
}
