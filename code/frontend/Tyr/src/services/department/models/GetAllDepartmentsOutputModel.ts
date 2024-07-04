import {Department} from "./Department";

/**
 * Output model for the get all departments.
 *
 * @param departments all departments
 */
export interface GetAllDepartmentsOutputModel {
    departments: Department[];
}