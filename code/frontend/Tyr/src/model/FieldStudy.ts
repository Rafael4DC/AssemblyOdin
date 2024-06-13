import {Module} from "./Module";
import {Department} from "./Department";

/**
 * SubCategory model
 *
 * @param id the id of the subcategory
 * @param name the name of the subcategory
 * @param description the description of the subcategory
 * @param modules the modules of the subcategory
 */
export interface FieldStudy {
    id?: number;
    name?: string;
    description?: string;
    department?: Department;
    modules?: Module[];
}
