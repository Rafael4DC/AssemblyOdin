import { makeApiRequest } from '../axios/apiRequest';
import {CurricularUnit} from "../model/CurricularUnit";

export class CurricularUnitService {

    static async getById(id: number): Promise<CurricularUnit> {
        return makeApiRequest('get', `/curricularunits/${id}`);
    }

    static async getAll(): Promise<CurricularUnit[]> {
        return makeApiRequest('get', '/curricularunits');
    }

    static async save(curricularUnitRequest: CurricularUnit): Promise<CurricularUnit> {
        return makeApiRequest('post', '/curricularunits/save', curricularUnitRequest);
    }

    static async update(curricularUnitRequest: CurricularUnit): Promise<CurricularUnit> {
        return makeApiRequest('put', '/curricularunits/update', curricularUnitRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/curricularunits/${id}`);
    }
}