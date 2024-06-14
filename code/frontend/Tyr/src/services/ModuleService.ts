import {makeApiRequest} from '../axios/apiRequest';
import {Module} from "../model/Module";

/**
 * Service to manage modules
 */
export class ModuleService {

    static basePath = '/modules';

    static async getById(id: number): Promise<Module> {
        return makeApiRequest('get', `${(ModuleService.basePath)}/${id}`);
    }

    static async getAll(): Promise<ModuleResponse> {
        return makeApiRequest('get', `${(ModuleService.basePath)}`);
    }

    static async save(moduleRequest: Module): Promise<Module> {
        return makeApiRequest('post', `${(ModuleService.basePath)}/save`, moduleRequest);
    }

    static async update(moduleRequest: Module): Promise<Module> {
        return makeApiRequest('put', `${(ModuleService.basePath)}/update`, moduleRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(ModuleService.basePath)}/${id}`);
    }
}

interface ModuleResponse {
    modules: Module[];
}