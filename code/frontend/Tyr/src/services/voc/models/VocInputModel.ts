/**
 * Input model for voc
 *
 * @param id the id of the voc
 * @param description the description of the voc
 * @param approved the approved of the voc
 * @param user the user of the voc
 * @param section the section of the voc
 * @param started the start date of the voc
 * @param ended the end date of the voc
 */
export interface VocInputModel {
    id?: number,
    description?: string,
    approved?: boolean,
    user?: number,
    section?: number,
    started?: string,
    ended?: string
}