import {makeApiRequest} from "../axios/apiRequest";
import {User} from "../model/User";

export class UserService {

    static async getSession(): Promise<User> {
        return makeApiRequest('get', `/users/session`);
    }

    static async getById(id: string): Promise<User> {
        return makeApiRequest('get', `/users/${id}`);
    }

    static async getAll(): Promise<User[]> {
        return makeApiRequest('get', '/users');
    }

    static async save(userRequest: User): Promise<User> {
        return makeApiRequest('post', '/users/save', userRequest);
    }

    static async update(userRequest: User): Promise<User> {
        return makeApiRequest('put', '/users/update', userRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `/users/${id}`);
    }
}


