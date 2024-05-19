package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.isStudent
import pt.isel.odin.controller.dto.tech.TechAttendanceResponse
import pt.isel.odin.model.Tech
import pt.isel.odin.model.copy
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.TechService

@Service
class TechServiceImpl(
    private val techRepository: TechRepository,
    private val userService: UserServiceImpl,
    private val classAttendanceService: ClassAttendanceServiceImpl
) : TechService {

    override fun getById(id: Long): Tech {
        return techRepository.findById(id).orElseThrow { NotFoundException("No Tech Found") }
    }

    override fun getAll(): List<Tech> {
        return techRepository.findAll()
    }

    override fun save(techRequest: Tech, email: String): Tech {
        val tech = techRequest.copy(
            teacher = techRequest.teacher ?: userService.getByEmail(email)
        )
        return techRepository.save(tech)
    }

    override fun update(techRequest: Tech): Tech {
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

    override fun delete(id: Long) {
        techRepository.deleteById(id)
    }

    override fun getByUser(email: String): List<Tech> {
        val user = userService.getByEmail(email) ?: throw NotFoundException("No User Found")
        return if (isStudent(email)) {
            techRepository.getByStudentId(user.id!!)
        } else {
            techRepository.getByUserId(user.id!!)
        }
    }

    override fun getMyTechsAttendance(email: String): List<TechAttendanceResponse> {
        val techs = getByUser(email)
        return techs.map { tech ->
            TechAttendanceResponse(
                tech,
                classAttendanceService.getByTechId(tech.id!!)
            )
        }
    }
}
