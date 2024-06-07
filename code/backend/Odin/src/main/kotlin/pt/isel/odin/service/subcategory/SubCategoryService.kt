package pt.isel.odin.service.subcategory

import org.springframework.stereotype.Service
import pt.isel.odin.model.SubCategory
import pt.isel.odin.repository.SubCategoryRepository
import pt.isel.odin.service.NotFoundException

@Service
class SubCategoryService(private val subCategoryRepository: SubCategoryRepository) {

    fun getById(id: Long): SubCategory {
        return subCategoryRepository.findById(id).orElseThrow { NotFoundException("No SubCategory Found") }
    }

    fun getAll(): List<SubCategory> {
        return subCategoryRepository.findAll()
    }

    fun save(subCategoryRequest: SubCategory): SubCategory {
        return subCategoryRepository.save(subCategoryRequest)
    }

    fun delete(id: Long) {
        subCategoryRepository.deleteById(id)
    }
}
