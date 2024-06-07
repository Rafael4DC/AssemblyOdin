package pt.isel.odin.service.module

import org.springframework.stereotype.Service
import pt.isel.odin.model.Module
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.service.NotFoundException

@Service
class ModuleService(private val moduleRepository: ModuleRepository) {

    fun getById(id: Long): Module {
        return moduleRepository.findById(id).orElseThrow { NotFoundException("No Module Found") }
    }

    fun getAll(): List<Module> {
        return moduleRepository.findAll()
    }

    fun save(moduleRequest: Module): Module {
        return moduleRepository.save(moduleRequest)
    }

    fun delete(id: Long) {
        moduleRepository.deleteById(id)
    }
}
