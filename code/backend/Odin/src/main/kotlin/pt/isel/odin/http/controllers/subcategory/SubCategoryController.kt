package pt.isel.odin.http.controllers.subcategory

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.model.SubCategory
import pt.isel.odin.service.subcategory.SubCategoryService

/**
 * Represents the controller that contains the endpoints related to the SubCategories.
 */
@RestController
@RequestMapping("/api/subcategories")
class SubCategoryController(private val subCategoryService: SubCategoryService) {

    /**
     * Get a SubCategory by id
     *
     * @param id the SubCategory id
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): SubCategory? {
        return subCategoryService.getById(id)
    }

    /**
     * Get all SubCategories
     */
    @GetMapping
    fun getAll(): List<SubCategory> {
        return subCategoryService.getAll()
    }

    /**
     * Save a SubCategory
     *
     * @param subCategoryRequest the SubCategory to save
     */
    @PostMapping("/save")
    fun save(@RequestBody subCategoryRequest: SubCategory): SubCategory {
        return subCategoryService.save(subCategoryRequest)
    }

    /**
     * Update a SubCategory
     *
     * @param subCategoryRequest the SubCategory to update
     */
    @PutMapping("/update")
    fun update(@RequestBody subCategoryRequest: SubCategory): SubCategory {
        return subCategoryService.save(subCategoryRequest)
    }

    /**
     * Delete a SubCategory by id
     *
     * @param id the SubCategory id
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        subCategoryService.delete(id)
    }
}
