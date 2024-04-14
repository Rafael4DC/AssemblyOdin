package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.tech.TechRequest
import pt.isel.odin.controller.dto.tech.toTech
import pt.isel.odin.model.CurricularUnit
import pt.isel.odin.model.Tech
import pt.isel.odin.model.User
import pt.isel.odin.model.copy
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.TechService

@Service
class TechServiceImpl(
    private val techRepository: TechRepository
) : TechService {

    override fun getById(id: Long): Tech {
        return techRepository.findById(id).orElseThrow { NotFoundException("No Tech Found") }
    }

    override fun getAll(): List<Tech> {
        return techRepository.findAll()
    }

    override fun save(techRequest: TechRequest): Tech {
        return techRepository.save(techRequest.toTech())
    }

    override fun update(techRequest: TechRequest): Tech {
        val tech = getById(techRequest.id!!)

        return techRepository.save(
            tech.copy(
                teacher = techRequest.teacherId?.let { User(it) } ?: tech.teacher,
                curricularUnit = techRequest.curricularUnitId?.let { CurricularUnit(it) } ?: tech.curricularUnit,
                date = techRequest.date ?: tech.date,
                summary = techRequest.summary ?: tech.summary
            )
        )
    }

    override fun delete(id: Long) {
        techRepository.deleteById(id)
    }
}
