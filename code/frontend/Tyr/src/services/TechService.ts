import { makeApiRequest } from '../axios/apiRequest';
import {Tech} from "../model/Tech";

export class TechService {

    static async getById(id: number): Promise<Tech> {
        return makeApiRequest('get', `/techs/${id}`);
    }

    static async getAll(): Promise<Tech[]> {
        return makeApiRequest('get', '/techs');
    }

    static async save(techRequest: Tech): Promise<Tech> {
        return makeApiRequest('post', '/techs/save', techRequest);
    }

    static async update(techRequest: Tech): Promise<Tech> {
        return makeApiRequest('put', '/techs/update', techRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/techs/${id}`);
    }
}