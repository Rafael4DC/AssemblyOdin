import { makeApiRequest } from '../axios/apiRequest';

export class CurricularUnitService {

    static async getById(id: number): Promise<Course> {
        return makeApiRequest('get', `/curricularunits/${id}`);
    }

    static async getAll(): Promise<Course[]> {
        return makeApiRequest('get', '/curricularunits');
    }

    static async save(curricularUnitRequest: Course): Promise<Course> {
        return makeApiRequest('post', '/curricularunits/save', curricularUnitRequest);
    }

    static async update(curricularUnitRequest: Course): Promise<Course> {
        return makeApiRequest('put', '/curricularunits/update', curricularUnitRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/curricularunits/${id}`);
    }
}