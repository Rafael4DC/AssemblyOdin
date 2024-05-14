import { makeApiRequest } from '../axios/apiRequest';
import {Voc} from "../model/Voc";

export class VocService {

    static async getById(id: number): Promise<Voc> {
        return makeApiRequest('get', `/vocs/${id}`);
    }

    static async getAll(): Promise<Voc[]> {
        return makeApiRequest('get', '/vocs');
    }

    static async save(vocRequest: Voc): Promise<Voc> {
        return makeApiRequest('post', '/vocs/save', vocRequest);
    }

    static async update(vocRequest: Voc): Promise<Voc> {
        return makeApiRequest('put', '/vocs/update', vocRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/vocs/${id}`);
    }

    static async getVocsByUser(): Promise<Voc[]> {
        return makeApiRequest('get', '/vocs/student');
    }
}