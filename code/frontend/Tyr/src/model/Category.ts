import {SubCategory} from "./SubCategory";

/**
 * Category model
 *
 * @param id the id of the category
 * @param name the name of the category
 * @param description the description of the category
 * @param subCategories the subcategories of the category
 */
export interface Category {
    id?: number;
    name?: string;
    description?: string;
    subCategories?: SubCategory[];
}
