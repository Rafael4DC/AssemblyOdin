package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.voc.VocSaveInputModel
import pt.isel.odin.controller.dto.voc.VocUpdateInputModel
import pt.isel.odin.model.Voc
import pt.isel.odin.model.copy
import pt.isel.odin.repository.CurricularUnitRepository
import pt.isel.odin.repository.StudentRepository
import pt.isel.odin.repository.VocRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.VocService

@Service
class VocServiceImpl(
    private val vocRepository: VocRepository,
    private val studentRepository: StudentRepository,
    private val curricularUnitRepository: CurricularUnitRepository
) : VocService {

    override fun getById(id: Long): Voc {
        return vocRepository.findById(id).orElseThrow { NotFoundException("No Voc Found") }
    }

    override fun getAll(): List<Voc> {
        return vocRepository.findAll()
    }

    override fun save(vocInputModel: VocSaveInputModel): Voc {
        val student = studentRepository.findById(vocInputModel.studentId)
            .orElseThrow { NotFoundException("No Student Found") }
        val curricularUnit =
            curricularUnitRepository.findById(vocInputModel.curricularUnitId)
                .orElseThrow { NotFoundException("No Curricular Unit Found") }

        return vocRepository.save(
            Voc(
                description = vocInputModel.description,
                approved = vocInputModel.approved,
                student = student,
                curricularUnit = curricularUnit,
                started = vocInputModel.started,
                ended = vocInputModel.ended
            )
        )
    }

    override fun update(vocInputModel: VocUpdateInputModel): Voc {
        val voc = vocRepository.findById(vocInputModel.id)
            .orElseThrow { NotFoundException("No Voc Found") }
        val student = vocInputModel.studentId?.let {
            studentRepository.findById(it).orElseThrow { NotFoundException("No Student Found") }
        } ?: voc.student
        val curricularUnit = vocInputModel.curricularUnitId?.let {
            curricularUnitRepository.findById(it).orElseThrow { NotFoundException("No Curricular Unit Found") }
        } ?: voc.curricularUnit

        return vocRepository.save(
            voc.copy(
                description = vocInputModel.description ?: voc.description,
                approved = vocInputModel.approved ?: voc.approved,
                student = student,
                curricularUnit = curricularUnit,
                started = vocInputModel.started ?: voc.started,
                ended = vocInputModel.ended ?: voc.ended
            )
        )
    }

    override fun delete(id: Long) {
        vocRepository.deleteById(id)
    }
}
