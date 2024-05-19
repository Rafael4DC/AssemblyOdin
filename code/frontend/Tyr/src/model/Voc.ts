import {Student} from "./Student";
import {Module} from './Module';

/**
 * Voc model
 *
 * @param id the id of the voc
 * @param description the description of the voc
 * @param approved if the voc is approved
 * @param student the student
 * @param module the module
 * @param started the start date of the voc
 * @param ended the end date of the voc
 */
export interface Voc {
    id?: number,
    description?: string,
    approved?: boolean,
    student?: Student,
    module?: Module,
    started?: string,
    ended?: string,
}
