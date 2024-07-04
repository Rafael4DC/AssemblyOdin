/**
 * Input model for a section
 *
 * @param id the id of the section
 * @param name the name of the section
 * @param summary the summary of the section
 * @param module the module of the section
 * @param students the students of the section
 */
export interface SectionInputModel {
    id?: number;
    name?: string;
    summary?: string;
    module?: number;
    students?: number[];
}