package pt.isel.odin.service.tech

import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.tech.models.TechAttendanceResponse
import pt.isel.odin.model.Tech
import pt.isel.odin.model.copy
import pt.isel.odin.model.isStudent
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.service.classattendance.ClassAttendanceService
import pt.isel.odin.service.NotFoundException
import pt.isel.odin.service.user.UserService

@Service
class TechService(
    private val techRepository: TechRepository,
    private val userService: UserService,
    private val classAttendanceService: ClassAttendanceService
) {

    fun getById(id: Long): Tech {
        return techRepository.findById(id).orElseThrow { NotFoundException("No Tech Found") }
    }

    fun getAll(): List<Tech> {
        return techRepository.findAll()
    }

    fun save(techRequest: Tech, email: String): Tech {
        val tech = techRequest.copy(
            teacher = techRequest.teacher ?: userService.getByEmail(email)
        )
        return techRepository.save(tech)
    }

    fun update(techRequest: Tech): Tech {
        val tech = getById(techRequest.id!!)

        return techRepository.save(
            tech.copy(
                teacher = techRequest.teacher ?: tech.teacher,
                curricularUnit = techRequest.module ?: tech.module,
                date = techRequest.date ?: tech.date,
                summary = techRequest.summary ?: tech.summary
            )
        )
    }

    fun delete(id: Long) {
        techRepository.deleteById(id)
    }

    fun getByUser(email: String): List<Tech> {
        val user = userService.getByEmail(email) ?: throw NotFoundException("No User Found")
        return if (user.role.isStudent()) {
            techRepository.getByStudentId(user.id!!)
        } else {
            techRepository.getByUserId(user.id!!)
        }
    }

    fun getMyTechsAttendance(email: String): List<TechAttendanceResponse> {
        val techs = getByUser(email)
        return techs.map { tech ->
            TechAttendanceResponse(
                tech,
                classAttendanceService.getByTechId(tech.id!!)
            )
        }
    }
}
