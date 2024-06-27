import {Module} from "../../module/models/Module";
import {User} from "../../user/models/User";

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