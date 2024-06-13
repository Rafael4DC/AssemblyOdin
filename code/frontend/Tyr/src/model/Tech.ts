import {User} from './User';
import {Module} from './Module';
import {Section} from "./Section";

/**
 * Tech model
 *
 * @param id the id of the tech
 * @param teacher the teacher of the tech
 * @param module the module of the tech
 * @param date the date of the tech
 * @param summary the summary of the tech
 *
 * @param attendance if the tech has attendance
 * @param attendedStudents the students that attended the tech
 */
export interface Tech {
    id?: number;
    teacher?: User;
    section?: Section;
    date?: string;
    summary?: string;
    missTech?: User[];

    attendance?: boolean;
}
