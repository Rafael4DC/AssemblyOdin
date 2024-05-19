import {makeApiRequest} from '../axios/apiRequest';
import {Category} from "../model/Category";

/**
 * Service to manage categories
 */
export class CategoryService {

    static basePath = '/categories';

    static async getById(id: number): Promise<Category> {
        return makeApiRequest('get', `${(CategoryService.basePath)}/${id}`);
    }

    static async getAll(): Promise<Category[]> {
        return makeApiRequest('get', CategoryService.basePath);
    }

    static async save(curricularUnitRequest: Category): Promise<Category> {
        return makeApiRequest('post', `${(CategoryService.basePath)}/save`, curricularUnitRequest);
    }

    static async update(curricularUnitRequest: Category): Promise<Category> {
        return makeApiRequest('put', `${(CategoryService.basePath)}/update`, curricularUnitRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(CategoryService.basePath)}/${id}`);
    }
}