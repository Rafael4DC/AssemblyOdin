import {apiRequest} from '../../axios/apiRequest';
import {CreationVocResult, DeleteVocResult, GetAllVocsResult, GetVocResult} from "./VocResult";
import {VocInputModel} from "./models/VocInputModel";

/**
 * Service to manage vocs
 */
export namespace VocService {

    const basePath = '/vocs';

    /**
     * Get voc by id
     *
     * @param id the id of the voc
     *
     * @returns the voc
     */
    export async function getById(id: number): Promise<GetVocResult> {
        return apiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all vocs
     *
     * @returns all vocs
     */
    export async function getAll(): Promise<GetAllVocsResult> {
        return apiRequest('get', basePath);
    }

    /**
     * Save a voc
     *
     * @param vocRequest the voc to save
     *
     * @returns the result of the creation
     */
    export async function save(vocRequest: VocInputModel): Promise<CreationVocResult> {
        return apiRequest('post', `${basePath}/save`, vocRequest);
    }

    /**
     * Update a voc
     *
     * @param vocRequest the voc to update
     *
     * @returns the result of the update
     */
    export async function update(vocRequest: VocInputModel): Promise<CreationVocResult> {
        return apiRequest('put', `${basePath}/update`, vocRequest);
    }

    /**
     * Delete a voc by id
     *
     * @param id the id of the voc
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteVocResult> {
        return apiRequest('delete', `${basePath}/${id}`);
    }

    /**
     * Get all vocs by user
     *
     * @returns all vocs
     */
    export async function getVocsByUser(): Promise<GetAllVocsResult> {
        return apiRequest('get', `${basePath}/user`);
    }
}
