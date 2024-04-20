import { makeApiRequest } from '../axios/apiRequest';

export class ClassAttendanceService {

    static async getById(id: number): Promise<ClassAttendance> {
        return makeApiRequest('get', `/classattendances/${id}`);
    }

    static async getAll(): Promise<ClassAttendance[]> {
        return makeApiRequest('get', '/classattendances');
    }

    static async save(classAttendanceRequest: ClassAttendanceRequest): Promise<ClassAttendance> {
        return makeApiRequest('post', '/classattendances/save', classAttendanceRequest);
    }

    static async update(classAttendanceRequest: ClassAttendanceRequest): Promise<ClassAttendance> {
        return makeApiRequest('put', '/classattendances/update', classAttendanceRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/classattendances/${id}`);
    }
}