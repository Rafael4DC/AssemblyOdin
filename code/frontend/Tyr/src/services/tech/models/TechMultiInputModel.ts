/**
 * Input model for tech
 *
 * @param id the id of the tech
 * @param teacher the teacher of the tech
 * @param section the section of the tech
 * @param date the date of the tech
 * @param summary the summary of the tech
 * @param missTech the students that missed the tech
 */
export interface TechMultiInputModel {
    section?: number;
    startDate?: string;
    endDate?: string;
    classTime?: string;
    classLengthHours?: number;
    dayOfWeek?: string;
}