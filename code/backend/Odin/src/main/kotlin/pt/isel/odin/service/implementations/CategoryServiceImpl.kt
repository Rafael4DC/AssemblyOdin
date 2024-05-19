package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.model.Category
import pt.isel.odin.repository.CategoryRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.CategoryService

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) :
    CategoryService {

    override fun getById(id: Long): Category {
        return categoryRepository.findById(id).orElseThrow { NotFoundException("No Category Found") }
    }

    override fun getAll(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun save(categoryRequest: Category): Category {
        return categoryRepository.save(categoryRequest)
    }

    override fun delete(id: Long) {
        categoryRepository.deleteById(id)
    }
}
