import {Module} from "./Module";
import {Category} from "./Category";

/**
 * SubCategory model
 *
 * @param id the id of the subcategory
 * @param name the name of the subcategory
 * @param description the description of the subcategory
 * @param modules the modules of the subcategory
 */
export interface SubCategory {
    id?: number;
    name?: string;
    description?: string;
    category?: Category;
    modules?: Module[];
}
