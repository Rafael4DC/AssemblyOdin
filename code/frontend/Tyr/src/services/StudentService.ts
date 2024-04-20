import { makeApiRequest } from '../axios/apiRequest';

export class StudentService {

    static async getById(id: number): Promise<Student> {
        return makeApiRequest('get', `/students/${id}`);
    }

    static async getAll(): Promise<Student[]> {
        return makeApiRequest('get', '/students');
    }

    static async save(studentRequest: StudentRequest): Promise<Student> {
        return makeApiRequest('post', '/students/save', studentRequest);
    }

    static async update(studentRequest: StudentRequest): Promise<Student> {
        return makeApiRequest('put', '/students/update', studentRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/students/${id}`);
    }
}