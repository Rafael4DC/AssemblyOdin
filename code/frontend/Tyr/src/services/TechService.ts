import {makeApiRequest} from '../axios/apiRequest';
import {Tech, TechRequest} from "../model/Tech";

/**
 * Service to manage techs
 */
export class TechService {

    static basePath = '/techs';

    static async getById(id: number): Promise<Tech> {
        return makeApiRequest('get', `${(TechService.basePath)}/${id}`);
    }

    static async getAll(): Promise<Tech[]> {
        return makeApiRequest('get', TechService.basePath);
    }

    static async save(techRequest: TechRequest): Promise<Tech> {
        return makeApiRequest('post', `${(TechService.basePath)}/save`, techRequest);
    }

    static async update(techRequest: TechRequest): Promise<Tech> {
        return makeApiRequest('put', `${(TechService.basePath)}/update`, techRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(TechService.basePath)}/${id}`);
    }

    static async getTechsByUser(): Promise<GetTechsByUserResponse> {
        return makeApiRequest('get', `${(TechService.basePath)}/user`);
    }

/*    static async getMyTechsAttendance(): Promise<TechsAttendance[]> {
        return makeApiRequest('get', `${(TechService.basePath)}/attendance`);
    }*/
}

interface GetTechsByUserResponse {
    techs: Tech[];
}