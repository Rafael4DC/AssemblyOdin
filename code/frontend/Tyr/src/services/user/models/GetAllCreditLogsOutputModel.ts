import {CreditLog} from "./CreditLog";

/**
 * GetAllUsersOutputModel model
 *
 * @param credits the list of credit logs
 */
export interface GetAllUsersOutputModel {
    credits: CreditLog[];
}