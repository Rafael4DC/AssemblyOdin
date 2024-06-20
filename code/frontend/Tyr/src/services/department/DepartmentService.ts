import {makeApiRequest} from '../../axios/apiRequest';
import {
    CreationDepartmentResult,
    DeleteDepartmentResult,
    GetAllDepartmentsResult,
    GetDepartmentResult
} from "./DeparmentResult";
import {DepartmentInputModel} from "./models/DepartmentInputModel";

/**
 * Service to manage departments
 */
export namespace DepartmentService {

    const basePath = '/departments';

    /**
     * Get department by id
     *
     * @param id the id of the department
     *
     * @returns the department
     */
    export async function getById(id: number): Promise<GetDepartmentResult> {
        return makeApiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all departments
     *
     * @returns all departments
     */
    export async function getAll(): Promise<GetAllDepartmentsResult> {
        return makeApiRequest('get', basePath);
    }

    /**
     * Save a department
     *
     * @param departmentRequest the department to save
     *
     * @returns the result of the creation
     */
    export async function save(departmentRequest: DepartmentInputModel): Promise<CreationDepartmentResult> {
        return makeApiRequest('post', `${basePath}/save`, departmentRequest);
    }

    /**
     * Update a department
     *
     * @param departmentRequest the department to update
     *
     * @returns the result of the update
     */
    export async function update(departmentRequest: DepartmentInputModel): Promise<CreationDepartmentResult> {
        return makeApiRequest('put', `${basePath}/update`, departmentRequest);
    }

    /**
     * Delete a department by id
     *
     * @param id the id of the department
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteDepartmentResult> {
        return makeApiRequest('delete', `${basePath}/${id}`);
    }
}

