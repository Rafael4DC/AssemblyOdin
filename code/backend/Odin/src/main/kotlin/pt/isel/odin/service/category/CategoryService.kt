package pt.isel.odin.service.category

import org.springframework.stereotype.Service
import pt.isel.odin.model.Category
import pt.isel.odin.repository.CategoryRepository
import pt.isel.odin.service.NotFoundException

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getById(id: Long): Category {
        return categoryRepository.findById(id).orElseThrow { NotFoundException("No Category Found") }
    }

    fun getAll(): List<Category> {
        return categoryRepository.findAll()
    }

    fun save(categoryRequest: Category): Category {
        return categoryRepository.save(categoryRequest)
    }

    fun delete(id: Long) {
        categoryRepository.deleteById(id)
    }
}
