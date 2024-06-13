import {makeApiRequest} from '../axios/apiRequest';
import {Section} from "../model/Section";

/**
 * Service to manage sections
 */
export class SectionService {

    static basePath = '/section';

    static async getById(id: number): Promise<Section> {
        return makeApiRequest('get', `${(SectionService.basePath)}/${id}`);
    }

    static async getAll(): Promise<Section[]> {
        return makeApiRequest('get', `${(SectionService.basePath)}`);
    }

    static async save(sectionRequest: Section): Promise<Section> {
        return makeApiRequest('post', `${(SectionService.basePath)}/save`, sectionRequest);
    }

    static async update(sectionRequest: Section): Promise<Section> {
        return makeApiRequest('put', `${(SectionService.basePath)}/update`, sectionRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(SectionService.basePath)}/${id}`);
    }
}