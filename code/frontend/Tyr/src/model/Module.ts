/**
 * Module model
 *
 * @param id the id of the module
 * @param name the name of the module
 * @param description the description of the module
 * @param tier the tier of the module
 */
export interface Module {
    id?: number;
    name?: string;
    description?: string;
    tier?: number;
}
