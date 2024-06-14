import {makeApiRequest} from '../axios/apiRequest';
import {Section, SectionRequest} from "../model/Section";

/**
 * Service to manage sections
 */
export class SectionService {

    static basePath = '/section';

    static async getById(id: number): Promise<Section> {
        return makeApiRequest('get', `${(SectionService.basePath)}/${id}`);
    }

    static async getAll(): Promise<GetSectionsResponse> {
        return makeApiRequest('get', `${(SectionService.basePath)}`);
    }

    static async save(sectionRequest: SectionRequest): Promise<Section> {
        return makeApiRequest('post', `${(SectionService.basePath)}/save`, sectionRequest);
    }

    static async update(sectionRequest: SectionRequest): Promise<Section> {
        return makeApiRequest('put', `${(SectionService.basePath)}/update`, sectionRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(SectionService.basePath)}/${id}`);
    }
}

interface GetSectionsResponse {
    sections: Section[];
}