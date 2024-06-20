import {FieldStudy} from "../../fieldstudy/models/FieldStudy";

/**
 * Department model
 *
 * @param id the id of the department
 * @param name the name of the department
 * @param description the description of the department
 * @param fieldsStudy the fields of study for a department
 */
export interface Department {
    id?: number;
    name?: string;
    description?: string;
    fieldsStudy?: FieldStudy[];
}
