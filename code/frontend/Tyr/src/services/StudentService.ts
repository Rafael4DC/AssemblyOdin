import {makeApiRequest} from '../axios/apiRequest';
import {Student} from "../model/Student";

/**
 * Service to manage students
 */
export class StudentService {

    static basePath = '/students';

    static async getById(id: number): Promise<Student> {
        return makeApiRequest('get', `${(StudentService.basePath)}/${id}`);
    }

    static async getAll(): Promise<Student[]> {
        return makeApiRequest('get', StudentService.basePath);
    }

    static async save(studentRequest: Student): Promise<Student> {
        return makeApiRequest('post', `${(StudentService.basePath)}/save`, studentRequest);
    }

    static async update(studentRequest: Student): Promise<Student> {
        return makeApiRequest('put', `${(StudentService.basePath)}/update`, studentRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(StudentService.basePath)}/${id}`);
    }
}