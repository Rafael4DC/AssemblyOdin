import {Module} from "./Module";

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
    modules?: Module[];
}
