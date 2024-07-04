import {Module} from "../../module/models/Module";
import {User} from "../../user/models/User";
import {SectionInputModel} from "./SectionInputModel";

/**
 * Section model
 *
 * @param id the id of the section
 * @param name the name of the section
 * @param summary the summary of the section
 * @param module the module of the section
 * @param students the students of the section
 */
export interface Section {
    id?: number;
    name?: string;
    module?: Module;
    students?: User[];
}

export function sectionToInput(section: Section): SectionInputModel {
    return {
        id: section.id,
        name: section.name,
        module: section.module.id,
        students: section.students.map(student => student.id)
    };
}

export function initSection(): Section {
    return {
        id: 0,
        name: '',
        module: {id: 0},
        students: []
    };
}