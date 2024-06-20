import { makeApiRequest } from '../../axios/apiRequest';
import {
    CreationSectionResult,
    DeleteSectionResult,
    GetAllSectionsResult,
    GetSectionResult
} from "./SectionResult";
import {SectionInputModel} from "./models/SectionInputModel";

/**
 * Service to manage sections
 */
export namespace SectionService {

    const basePath = '/sections';

    /**
     * Get a section by id
     *
     * @param id the id of the section
     *
     * @returns the section
     */
    export async function getById(id: number): Promise<GetSectionResult> {
        return makeApiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all sections
     *
     * @returns all sections
     */
    export async function getAll(): Promise<GetAllSectionsResult> {
        return makeApiRequest('get', basePath);
    }

    /**
     * Save a section
     *
     * @param sectionRequest the section to save
     *
     * @returns the result of the creation
     */
    export async function save(sectionRequest: SectionInputModel): Promise<CreationSectionResult> {
        return makeApiRequest('post', `${basePath}/save`, sectionRequest);
    }

    /**
     * Update a section
     *
     * @param sectionRequest the section to update
     *
     * @returns the result of the update
     */
    export async function update(sectionRequest: SectionInputModel): Promise<CreationSectionResult> {
        return makeApiRequest('put', `${basePath}/update`, sectionRequest);
    }

    /**
     * Delete a section by id
     *
     * @param id the id of the section
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteSectionResult> {
        return makeApiRequest('delete', `${basePath}/${id}`);
    }
}
