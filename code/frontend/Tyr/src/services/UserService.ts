import {makeApiRequest} from "../axios/apiRequest";
import {User} from "../model/User";
import {UserDTO} from "../model/dtos/user/UserDTO";

export class UserService {
  static async getById(id: number): Promise<User> {
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


