import {makeApiRequest} from '../axios/apiRequest';
import {Department} from "../model/Department";

/**
 * Service to manage departments
 */
export class DepartmentService {

    static basePath = '/department';

    static async getById(id: number): Promise<Department> {
        return makeApiRequest('get', `${(DepartmentService.basePath)}/${id}`);
    }

    static async getAll(): Promise<Department[]> {
        return makeApiRequest('get', DepartmentService.basePath);
    }

    static async save(departmentRequest: Department): Promise<Department> {
        return makeApiRequest('post', `${(DepartmentService.basePath)}/save`, departmentRequest);
    }

    static async update(departmentRequest: Department): Promise<Department> {
        return makeApiRequest('put', `${(DepartmentService.basePath)}/update`, departmentRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(DepartmentService.basePath)}/${id}`);
    }
}