import {makeApiRequest} from '../axios/apiRequest';
import {ClassAttendance} from "../model/ClassAtendance";

/**
 * Service to manage class attendances
 */
export class ClassAttendanceService {

    static basePath = '/classattendances';

    static async getById(id: number): Promise<ClassAttendance> {
        return makeApiRequest('get', `${(ClassAttendanceService.basePath)}/${id}`);
    }

    static async getAll(): Promise<ClassAttendance[]> {
        return makeApiRequest('get', ClassAttendanceService.basePath);
    }

    static async save(classAttendanceRequest: ClassAttendance): Promise<ClassAttendance> {
        return makeApiRequest('post', `${(ClassAttendanceService.basePath)}/save`, classAttendanceRequest);
    }

    static async update(classAttendanceRequest: ClassAttendance): Promise<ClassAttendance> {
        return makeApiRequest('put', `${(ClassAttendanceService.basePath)}/update`, classAttendanceRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(ClassAttendanceService.basePath)}/${id}`);
    }
}