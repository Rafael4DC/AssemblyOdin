import {User} from '../../user/models/User';
import {Section} from "../../section/models/Section";

/**
 * Tech model
 *
 * @param id the id of the tech
 * @param teacher the teacher of the tech
 * @param section the section of the tech
 * @param date the date of the tech
 * @param summary the summary of the tech
 * @param missTech the missing tech of the tech
 */
export interface Tech {
    id?: number;
    teacher?: User;
    section?: Section;
    date?: string;
    summary?: string;
    missTech?: User[];
}
