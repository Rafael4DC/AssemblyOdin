package pt.isel.odin.service.interfaces

import pt.isel.odin.model.Category

/**
 * Service for Categories.
 */
interface CategoryService {

    /**
     * Gets a Category by its id.
     *
     * @param id the curricular unit id.
     */
    fun getById(id: Long): Category

    /**
     * Gets all Categories.
     */
    fun getAll(): List<Category>

    /**
     * Saves or Updates a Category.
     *
     * @param categoryRequest the curricular unit to save.
     */
    fun save(categoryRequest: Category): Category

    /**
     * Deletes a Category by its id.
     *
     * @param id the Category id.
     */
    fun delete(id: Long)
}
