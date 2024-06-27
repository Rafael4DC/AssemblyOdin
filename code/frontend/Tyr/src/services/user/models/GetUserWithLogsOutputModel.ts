import {Role} from "./Role";
import {CreditLog} from "./CreditLog";

export interface GetUserWithLogsOutputModel {
    id?: number,
    email?: string,
    username?: string,
    credits?: number,
    role?: Role,
    logs?: CreditLog[]
}