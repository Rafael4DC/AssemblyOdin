import {User} from '../../user/models/User';
import {Section} from "../../section/models/Section";
import {TechMultiInputModel} from "./TechMultiInputModel";
import {TechInputModel} from "./TechInputModel";

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
    started?: string;
    ended?: string;
    summary?: string;
    missTech?: User[];

    startDate?: string;
    endDate?: string;
    classTime?: string;
    classLengthHours?: number;
    dayOfWeek?: string;
}

export function initTech(): Tech {
    const date = new Date().toISOString().split('T')[0]

    return {
        teacher: {id: 0},
        section: {id: 0},
        started: date + 'T10:00',
        ended: date + 'T11:00',
        summary: '',
        missTech: [],
        startDate: date,
        endDate: date,
        classTime: '10:00',
        classLengthHours: 0,
        dayOfWeek: 'Monday'
    }

}

export function techToInput(tech: Tech): TechInputModel {
    return {
        id: tech.id,
        teacher: tech.teacher?.id,
        section: tech.section?.id,
        started: tech.started,
        ended: tech.ended,
        summary: tech.summary,
        missTech: tech.missTech?.map(miss => miss.id)
    }
}

export function techToTechMultiInput(tech: Tech): TechMultiInputModel {
    return {
        section: tech.section?.id,
        startDate: tech.startDate,
        endDate: tech.endDate,
        classTime: tech.classTime,
        classLengthHours: Number(tech.classLengthHours),
        dayOfWeek: tech.dayOfWeek.toUpperCase()
    }
}
