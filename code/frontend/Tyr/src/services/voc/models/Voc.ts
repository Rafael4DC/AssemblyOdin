import {User} from "../../user/models/User";
import {Section} from "../../section/models/Section";
import {VocInputModel} from "./VocInputModel";

/**
 * Voc model
 *
 * @param id the id of the voc
 * @param description the description of the voc
 * @param approved if the voc is approved
 * @param user the user
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

export function vocToInput(voc: Voc): VocInputModel {
    return {
        id: voc.id,
        description: voc.description,
        approved: voc.approved,
        user: voc.user.id,
        section: voc.section.id,
        started: voc.started,
        ended: voc.ended,
    }
}

export function initVoc(): Voc {
    const date = new Date().toISOString().split('T')[0]

    return {
        description: "",
        started: date + 'T10:00',
        ended: date + 'T11:00',
        user: {id: 0},
        approved: false,
        section: {id: 0},
    }
}