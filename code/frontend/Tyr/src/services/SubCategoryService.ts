import {makeApiRequest} from '../axios/apiRequest';
import {SubCategory} from "../model/SubCategory";

/**
 * Service to manage subcategories
 */
export class SubCategoryService {

    static basePath = '/subcategories';

    static async getById(id: number): Promise<SubCategory> {
        return makeApiRequest('get', `${(SubCategoryService.basePath)}/${id}`);
    }

    static async getAll(): Promise<SubCategory[]> {
        return makeApiRequest('get', SubCategoryService.basePath);
    }

    static async save(subCategoryRequest: SubCategory): Promise<SubCategory> {
        return makeApiRequest('post', `${(SubCategoryService.basePath)}/save`, subCategoryRequest);
    }

    static async update(subCategoryRequest: SubCategory): Promise<SubCategory> {
        return makeApiRequest('put', `${(SubCategoryService.basePath)}/update`, subCategoryRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(SubCategoryService.basePath)}/${id}`);
    }
}