package pt.isel.odin.http.controllers.category

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.model.Category
import pt.isel.odin.service.category.CategoryService

/**
 * Represents the controller that contains the endpoints related to the Categories.
 */
@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {

    /**
     * Get a Category by id
     *
     * @param id the Curricular Unit id
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Category? {
        return categoryService.getById(id)
    }

    /**
     * Get all Categories
     */
    @GetMapping
    fun getAll(): List<Category> {
        return categoryService.getAll()
    }

    /**
     * Save a Category
     *
     * @param categoryRequest the Curricular Unit to save
     */
    @PostMapping("/save")
    fun save(@RequestBody categoryRequest: Category): Category {
        return categoryService.save(categoryRequest)
    }

    /**
     * Update a Category
     *
     * @param categoryRequest the Category to update
     */
    @PutMapping("/update")
    fun update(@RequestBody categoryRequest: Category): Category {
        return categoryService.save(categoryRequest)
    }

    /**
     * Delete a Category by id
     *
     * @param id the Category id
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        categoryService.delete(id)
    }
}
