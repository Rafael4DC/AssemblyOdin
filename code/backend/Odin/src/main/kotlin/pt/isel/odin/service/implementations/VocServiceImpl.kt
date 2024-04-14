package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.voc.VocRequest
import pt.isel.odin.controller.dto.voc.toVoc
import pt.isel.odin.model.CurricularUnit
import pt.isel.odin.model.Student
import pt.isel.odin.model.Voc
import pt.isel.odin.model.copy
import pt.isel.odin.repository.VocRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.VocService

@Service
class VocServiceImpl(
    private val vocRepository: VocRepository
) : VocService {

    override fun getById(id: Long): Voc {
        return vocRepository.findById(id).orElseThrow { NotFoundException("No Voc Found") }
    }

    override fun getAll(): List<Voc> {
        return vocRepository.findAll()
    }

    override fun save(vocRequest: VocRequest): Voc {
        return vocRepository.save(vocRequest.toVoc())
    }

    override fun update(vocRequest: VocRequest): Voc {
        val voc = getById(vocRequest.id!!)

        return vocRepository.save(
            voc.copy(
                description = vocRequest.description ?: voc.description,
                approved = vocRequest.approved ?: voc.approved,
                student = vocRequest.studentId?.let { Student(it) } ?: voc.student,
                curricularUnit = vocRequest.curricularUnitId?.let { CurricularUnit(it) } ?: voc.curricularUnit,
                started = vocRequest.started ?: voc.started,
                ended = vocRequest.ended ?: voc.ended
            )
        )
    }

    override fun delete(id: Long) {
        vocRepository.deleteById(id)
    }
}
