import {FieldStudy} from "../../fieldstudy/models/FieldStudy";
import {DepartmentInputModel} from "./DepartmentInputModel";

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

export function departmentToInput(department: Department): DepartmentInputModel {
    return {
        id: department.id,
        name: department.name,
        description: department.description
    };
}