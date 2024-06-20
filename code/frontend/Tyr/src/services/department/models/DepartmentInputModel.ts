/**
 * Input model for department
 *
 * @param id the id of the department
 * @param name the name of the department
 * @param description the description of the department
 */
export interface DepartmentInputModel {
    id?: number;
    name?: string;
    description?: string;
}