import { makeApiRequest } from '../axios/apiRequest';

export class VocService {

    static async getById(id: number): Promise<Voc> {
        return makeApiRequest('get', `/vocs/${id}`);
    }

    static async getAll(): Promise<Voc[]> {
        return makeApiRequest('get', '/vocs');
    }

    static async save(vocRequest: VocRequest): Promise<Voc> {
        return makeApiRequest('post', '/vocs/save', vocRequest);
    }

    static async update(vocRequest: VocRequest): Promise<Voc> {
        return makeApiRequest('put', '/vocs/update', vocRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/vocs/${id}`);
    }
}