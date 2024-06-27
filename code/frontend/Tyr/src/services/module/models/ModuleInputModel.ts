/**
 * Input model for module
 *
 * @param id the id of the module
 * @param name the name of the module
 * @param description the description of the module
 */
export interface ModuleInputModel {
    id?: number;
    name?: string;
    description?: string;
    fieldStudy?: number;
}