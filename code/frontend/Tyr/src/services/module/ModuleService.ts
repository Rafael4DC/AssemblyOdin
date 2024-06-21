import {apiRequest} from '../../axios/apiRequest';
import {CreationModuleResult, DeleteModuleResult, GetAllModulesResult, GetModuleResult} from "./ModuleResult";
import {ModuleInputModel} from "./models/ModuleInputModel";

/**
 * Service to manage modules
 */
export namespace ModuleService {

    const basePath = '/modules';

    /**
     * Get module by id
     *
     * @param id the id of the module
     *
     * @returns the module
     */
    export async function getById(id: number): Promise<GetModuleResult> {
        return apiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all modules
     *
     * @returns all modules
     */
    export async function getAll(): Promise<GetAllModulesResult> {
        return apiRequest('get', basePath);
    }

    /**
     * Save a module
     *
     * @param moduleRequest the module to save
     *
     * @returns the result of the creation
     */
    export async function save(moduleRequest: ModuleInputModel): Promise<CreationModuleResult> {
        return apiRequest('post', `${basePath}/save`, moduleRequest);
    }

    /**
     * Update a module
     *
     * @param moduleRequest the module to update
     *
     * @returns the result of the update
     */
    export async function update(moduleRequest: ModuleInputModel): Promise<CreationModuleResult> {
        return apiRequest('put', `${basePath}/update`, moduleRequest);
    }

    /**
     * Delete a module by id
     *
     * @param id the id of the module
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteModuleResult> {
        return apiRequest('delete', `${basePath}/${id}`);
    }
}
