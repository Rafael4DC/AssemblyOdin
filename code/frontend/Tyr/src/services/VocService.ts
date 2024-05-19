import {makeApiRequest} from '../axios/apiRequest';
import {Voc} from "../model/Voc";

/**
 * Service to manage vocs
 */
export class VocService {

    static basePath = '/vocs';

    static async getById(id: number): Promise<Voc> {
        return makeApiRequest('get', `${(VocService.basePath)}/${id}`);
    }

    static async getAll(): Promise<Voc[]> {
        return makeApiRequest('get', VocService.basePath);
    }

    static async save(vocRequest: Voc): Promise<Voc> {
        return makeApiRequest('post', `${(VocService.basePath)}/save`, vocRequest);
    }

    static async update(vocRequest: Voc): Promise<Voc> {
        return makeApiRequest('put', `${(VocService.basePath)}/update`, vocRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(VocService.basePath)}/${id}`);
    }

    static async getVocsByUser(): Promise<Voc[]> {
        return makeApiRequest('get', `${(VocService.basePath)}/student`);
    }
}