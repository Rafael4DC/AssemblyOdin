import {apiRequest} from '../../axios/apiRequest';
import {
    CreationFieldStudyResult,
    DeleteFieldStudyResult,
    GetAllFieldsStudyResult,
    GetFieldStudyResult
} from "./FieldStudyResult";
import {FieldStudyInputModel} from "./models/FieldStudyInputModel";

/**
 * Service to manage field studies
 */
export namespace FieldStudyService {

    const basePath = '/fieldsstudy';

    /**
     * Get field of study by id
     *
     * @param id the id of the field of study
     *
     * @returns the field of study
     */
    export async function getById(id: number): Promise<GetFieldStudyResult> {
        return apiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all fields of study
     *
     * @returns all fields of study
     */
    export async function getAll(): Promise<GetAllFieldsStudyResult> {
        return apiRequest('get', basePath);
    }

    /**
     * Save a field of study
     *
     * @param fieldStudyRequest the field of study to save
     *
     * @returns the result of the creation
     */
    export async function save(fieldStudyRequest: FieldStudyInputModel): Promise<CreationFieldStudyResult> {
        return apiRequest('post', `${basePath}/save`, fieldStudyRequest);
    }

    /**
     * Update a field of study
     *
     * @param fieldStudyRequest the field of study to update
     *
     * @returns the result of the update
     */
    export async function update(fieldStudyRequest: FieldStudyInputModel): Promise<CreationFieldStudyResult> {
        return apiRequest('put', `${basePath}/update`, fieldStudyRequest);
    }

    /**
     * Delete a field of study by id
     *
     * @param id the id of the field of study
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteFieldStudyResult> {
        return apiRequest('delete', `${basePath}/${id}`);
    }
}
