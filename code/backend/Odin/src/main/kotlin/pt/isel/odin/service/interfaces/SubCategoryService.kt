package pt.isel.odin.service.interfaces

import pt.isel.odin.model.SubCategory

/**
 * Service for SubCategories.
 */
interface SubCategoryService {

    /**
     * Gets a SubCategory by its id.
     *
     * @param id the SubCategory id.
     */
    fun getById(id: Long): SubCategory

    /**
     * Gets all SubCategories.
     */
    fun getAll(): List<SubCategory>

    /**
     * Saves or Updates a SubCategory.
     *
     * @param subCategoryRequest the SubCategory to save.
     */
    fun save(subCategoryRequest: SubCategory): SubCategory

    /**
     * Deletes a SubCategory by its id.
     *
     * @param id the SubCategory id.
     */
    fun delete(id: Long)
}
