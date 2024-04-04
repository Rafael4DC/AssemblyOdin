package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.tech.TechSaveInputModel
import pt.isel.odin.controller.dto.tech.TechUpdateInputModel
import pt.isel.odin.model.Tech
import pt.isel.odin.model.copy
import pt.isel.odin.repository.CurricularUnitRepository
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.TechService

@Service
class TechServiceImpl(
    private val techRepository: TechRepository,
    private val userRepository: UserRepository,
    private val curricularUnitRepository: CurricularUnitRepository
) : TechService {

    override fun getById(id: Long): Tech {
        return techRepository.findById(id).orElseThrow { NotFoundException("No Tech Found") }
    }

    override fun getAll(): List<Tech> {
        return techRepository.findAll()
    }

    override fun save(techSaveInputModel: TechSaveInputModel): Tech {
        val teacher =
            userRepository.findById(techSaveInputModel.teacherId).orElseThrow { NotFoundException("No Teacher Found") }
        val curricularUnit =
            curricularUnitRepository.findById(techSaveInputModel.curricularUnitId)
                .orElseThrow { NotFoundException("No Curricular Unit Found") }
        return techRepository.save(
            Tech(
                teacher = teacher,
                curricularUnit = curricularUnit,
                date = techSaveInputModel.date,
                summary = techSaveInputModel.summary
            )
        )
    }

    override fun update(techUpdateInputModel: TechUpdateInputModel): Tech {
        val tech = techRepository.findById(techUpdateInputModel.id)
            .orElseThrow { NotFoundException("No Tech Found") }
        val teacher = techUpdateInputModel.teacherId
            ?.let { userRepository.findById(it).orElseThrow { NotFoundException("No Teacher Found") } } ?: tech.teacher
        val curricularUnit = techUpdateInputModel.curricularUnitId
            ?.let {
                curricularUnitRepository.findById(it).orElseThrow { NotFoundException("No Curricular Unit Found") }
            } ?: tech.curricularUnit

        return techRepository.save(
            tech.copy(
                teacher = teacher,
                curricularUnit = curricularUnit,
                date = techUpdateInputModel.date ?: tech.date,
                summary = techUpdateInputModel.summary ?: tech.summary
            )
        )
    }

    override fun delete(id: Long) {
        techRepository.deleteById(id)
    }
}
