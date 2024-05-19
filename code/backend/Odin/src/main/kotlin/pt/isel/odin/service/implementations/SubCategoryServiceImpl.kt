package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.model.SubCategory
import pt.isel.odin.repository.SubCategoryRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.SubCategoryService

@Service
class SubCategoryServiceImpl(private val subCategoryRepository: SubCategoryRepository) :
    SubCategoryService {

    override fun getById(id: Long): SubCategory {
        return subCategoryRepository.findById(id).orElseThrow { NotFoundException("No SubCategory Found") }
    }

    override fun getAll(): List<SubCategory> {
        return subCategoryRepository.findAll()
    }

    override fun save(subCategoryRequest: SubCategory): SubCategory {
        return subCategoryRepository.save(subCategoryRequest)
    }

    override fun delete(id: Long) {
        subCategoryRepository.deleteById(id)
    }
}
