import {Student} from "./Student";
import {CurricularUnit} from "./CurricularUnit";
import {Date} from "./Date";

export interface Voc {
    id?: number,
    description?: string,
    approved?: boolean,
    student?: Student,
    curricularUnit?: CurricularUnit,
    started?: Date,
    ended?: Date,
}
