import {makeApiRequest} from "../axios/apiRequest";
import {User} from "../model/User";

/**
 * Service to manage users
 */
export class UserService {

    static basePath = '/users';

    static async getSession(): Promise<User> {
        return makeApiRequest('get', `${(UserService.basePath)}/session`);
    }

    static async getById(id: string): Promise<User> {
        return makeApiRequest('get', `${(UserService.basePath)}/${id}`);
    }

    static async getAll(): Promise<User[]> {
        return makeApiRequest('get', UserService.basePath);
    }

    static async save(userRequest: User): Promise<User> {
        return makeApiRequest('post', `${(UserService.basePath)}/save`, userRequest);
    }

    static async update(userRequest: User): Promise<User> {
        return makeApiRequest('put', `${(UserService.basePath)}/update`, userRequest);
    }

    static async delete(id: number): Promise<void> {
        return makeApiRequest('delete', `${(UserService.basePath)}/${id}`);
    }
}


