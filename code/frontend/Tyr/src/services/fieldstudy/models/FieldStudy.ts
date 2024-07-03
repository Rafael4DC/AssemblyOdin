import {Module} from "../../module/models/Module";
import {Department} from "../../department/models/Department";
import {FieldStudyInputModel} from "./FieldStudyInputModel";

/**
 * Field Study model
 *
 * @param id the id of the field study
 * @param name the name of the field study
 * @param description the description of the field study
 * @param department the department of the field study
 * @param modules the modules of the field study
 */
export interface FieldStudy {
    id?: number;
    name?: string;
    description?: string;
    department?: Department;
    modules?: Module[];
}

export function fieldStudyToInput(fieldStudy: FieldStudy): FieldStudyInputModel {
    return {
        id: fieldStudy.id,
        name: fieldStudy.name,
        description: fieldStudy.description,
        department: fieldStudy.department?.id
    };
}