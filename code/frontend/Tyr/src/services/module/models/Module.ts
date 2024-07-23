import {FieldStudy} from "../../fieldstudy/models/FieldStudy";
import {ModuleInputModel} from "./ModuleInputModel";

/**
 * Module model
 *
 * @param id the id of the module
 * @param name the name of the module
 * @param description the description of the module
 * @param fieldStudy the field study of the module
 * @param tier the tier of the module
 */
export interface Module {
    id?: number;
    name?: string;
    description?: string;
    fieldStudy?: FieldStudy;
    tier?: number;
}

export function moduleToInput(module: Module): ModuleInputModel {
    return {
        id: module.id,
        name: module.name,
        description: module.description,
        fieldStudy: module.fieldStudy?.id
    };
}