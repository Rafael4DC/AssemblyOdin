import { RoleOptions, User } from '../model/GetUserInfoOutputModel';
import {makeApiRequest} from "../axios/apiRequest";

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


/*export async function getUserInfo(): Promise<User> {
  console.log('Getting user info');
  const a = await makeApiRequest('get', '/users')
  //.then(data => console.log('Data received:', data))
  //.catch(err => console.error('Error occurred:', err.message));

  return {
    id: 1,
    name: 'Example Name',
    email: 'example@email.com',
    role: RoleOptions.Admin,
    credits: 100,
  };
}*/

