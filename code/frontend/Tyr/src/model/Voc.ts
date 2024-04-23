import {Student} from "./Student";
import {CurricularUnit} from "./CurricularUnit";

export interface Voc {
    id?: number,
    description?: string,
    approved?: boolean,
    student?: Student,
    curricularUnit?: CurricularUnit,
    started?: Date,
    ended?: Date,
    studentId?: number,
    curricularUnitId?: number
}
