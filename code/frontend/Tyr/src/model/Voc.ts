import {Module} from './Module';
import {User} from "./User";
import {Section} from "./Section";

/**
 * Voc model
 *
 * @param id the id of the voc
 * @param description the description of the voc
 * @param approved if the voc is approved
 * @param student the student
 * @param section the section
 * @param started the start date of the voc
 * @param ended the end date of the voc
 */
export interface Voc {
    id?: number,
    description?: string,
    approved?: boolean,
    user?: User,
    section?: Section,
    started?: string,
    ended?: string,
}
