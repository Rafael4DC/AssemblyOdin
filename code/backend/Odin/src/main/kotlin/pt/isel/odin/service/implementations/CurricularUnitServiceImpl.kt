package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.curricularunit.CurricularUnitRequest
import pt.isel.odin.controller.dto.curricularunit.toCurricularUnit
import pt.isel.odin.model.CurricularUnit
import pt.isel.odin.repository.CurricularUnitRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.CurricularUnitService

@Service
class CurricularUnitServiceImpl(private val curricularUnitRepository: CurricularUnitRepository) :
    CurricularUnitService {

    override fun getById(id: Long): CurricularUnit {
        return curricularUnitRepository.findById(id).orElseThrow { NotFoundException("No Curricular Unit Found") }
    }

    override fun getAll(): List<CurricularUnit> {
        return curricularUnitRepository.findAll()
    }

    override fun save(curricularUnitRequest: CurricularUnitRequest): CurricularUnit {
        return curricularUnitRepository.save(curricularUnitRequest.toCurricularUnit())
    }

    override fun delete(id: Long) {
        curricularUnitRepository.deleteById(id)
    }
}
