import {FieldStudy} from "./FieldStudy";

/**
 * Category model
 *
 * @param id the id of the category
 * @param name the name of the category
 * @param description the description of the category
 * @param subCategories the subcategories of the category
 */
export interface Department {
    id?: number;
    name?: string;
    description?: string;
    fieldsStudy?: FieldStudy[];
}
