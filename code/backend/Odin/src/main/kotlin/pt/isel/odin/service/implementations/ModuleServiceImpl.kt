package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.model.Module
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.ModuleService

@Service
class ModuleServiceImpl(private val moduleRepository: ModuleRepository) :
    ModuleService {

    override fun getById(id: Long): Module {
        return moduleRepository.findById(id).orElseThrow { NotFoundException("No Module Found") }
    }

    override fun getAll(): List<Module> {
        return moduleRepository.findAll()
    }

    override fun save(moduleRequest: Module): Module {
        return moduleRepository.save(moduleRequest)
    }

    override fun delete(id: Long) {
        moduleRepository.deleteById(id)
    }
}
