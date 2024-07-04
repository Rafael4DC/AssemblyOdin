import {makeApiRequest} from '../axios/apiRequest';
import {FieldStudy} from "../model/FieldStudy";

/**
 * Service to manage fields study
 */
export class FieldStudyService {

    static basePath = '/fieldstudy';

    static async getById(id: number): Promise<FieldStudy> {
        return makeApiRequest('get', `${(FieldStudyService.basePath)}/${id}`);
    }

    static async getAll(): Promise<FieldStudy[]> {
        return makeApiRequest('get', FieldStudyService.basePath);
    }

    static async save(fieldStudyRequest: FieldStudy): Promise<FieldStudy> {
        return makeApiRequest('post', `${(FieldStudyService.basePath)}/save`, fieldStudyRequest);
    }

    static async update(fieldStudyRequest: FieldStudy): Promise<FieldStudy> {
        return makeApiRequest('put', `${(FieldStudyService.basePath)}/update`, fieldStudyRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(FieldStudyService.basePath)}/${id}`);
    }
}