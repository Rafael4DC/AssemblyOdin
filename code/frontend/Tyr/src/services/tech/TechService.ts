import {apiRequest} from '../../axios/apiRequest';
import {
    CreationTechResult,
    DeleteTechResult,
    GetAllTechsResult,
    GetTechResult,
    ListCreationTechResult
} from "./TechResult";
import {TechInputModel} from "./models/TechInputModel";
import {TechMultiInputModel} from "./models/TechMultiInputModel";

/**
 * Service to manage techs
 */
export namespace TechService {

    const basePath = '/techs';

    /**
     * Get tech by id
     *
     * @param id the id of the tech
     *
     * @returns the tech
     */
    export async function getById(id: number): Promise<GetTechResult> {
        return apiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all techs
     *
     * @returns all techs
     */
    export async function getAll(): Promise<GetAllTechsResult> {
        return apiRequest('get', basePath);
    }

    /**
     * Save a tech
     *
     * @param techRequest the tech to save
     *
     * @returns the result of the creation
     */
    export async function save(techRequest: TechInputModel): Promise<CreationTechResult> {
        return apiRequest('post', `${basePath}/save`, techRequest);
    }

    /**
     * Save multiple techs
     *
     * @param techRequest the techs to save
     *
     * @returns the result of the creation
     */
    export async function saveMultiple(techRequest: TechMultiInputModel): Promise<ListCreationTechResult> {
        return apiRequest('post', `${basePath}/savemultiple`, techRequest);
    }

    /**
     * Update a tech
     *
     * @param techRequest the tech to update
     *
     * @returns the result of the update
     */
    export async function update(techRequest: TechInputModel): Promise<CreationTechResult> {
        return apiRequest('put', `${basePath}/update`, techRequest);
    }

    /**
     * Delete a tech by id
     *
     * @param id the id of the tech
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteTechResult> {
        return apiRequest('delete', `${basePath}/${id}`);
    }

    /**
     * Get techs by user
     *
     * @returns the techs
     */
    export async function getTechsByUser(): Promise<GetAllTechsResult> {
        return apiRequest('get', `${basePath}/user`);
    }
}
